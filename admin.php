<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ministarstvo</title>
        <link href="css/stil.css" rel="stylesheet" type="text/css" />
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>

        <script type="text/javascript">
        function opis(){
			var text="SuperAdmin - mogucnost mijenjanja/dodavanja/brisanja korisnika, novosti i ministarstava\n";
			text+="Admin - mogucnost mijenjanja/dodavanja/brisanja novosti i ministarstava\n";
			text+="Moderator - mogucnost mijenjanja/dodavanja/brisanja novosti\n";
			alert(text);
		}
		function promjenasifre(){
			if(document.promjena.novi_pass.value==document.promjena.novi_pass1.value)
			{
				document.promjena.vrij.value=1;
				document.promjena.submit();
			}
			else
				alert("Nova sifra i njena potvrda nisu iste");
		}
                function vidikorisnike(){
                    
                    jQuery.get("elementi.php?korisnici=test", function(data){
                        $('#izmjena').show('slow');
                        jQuery('#izmjena').html(data);

                    });
                }
        </script>
    </head>
<?php
session_start();
require_once("baza.php");
if(isset($_POST['prijava'])){
	$user=$_POST['kor_ime'];
	$pass=sha1($_POST['sifra']);
	$baza=new baza();
	$con=$baza->konektujse();
	$sql="select * from korisnici where user='%s' and pass='%s'";
	$res=mysql_query(sprintf($sql, mysql_real_escape_string($user),mysql_real_escape_string($pass)));
	while($row=mysql_fetch_array($res)){
		$_SESSION['logovan']=true;
		$_SESSION['id']=$row['id'];
		$_SESSION['user']=$row['user'];
		$_SESSION['tip']=$row['tip'];
		$sql1="select * from sadrzaj where pozicija='superadmin' or pozicija='admin_all'";
		$res1=mysql_query($sql1,$con);
		while($row1=mysql_fetch_array($res1)){
			if($row1['pozicija']=="superadmin")
				$_SESSION['superadmin']=$row1['kod'];
			if($row1['pozicija']=="admin_all")
				$_SESSION['admin_all']=$row1['kod'];
		}
		
	}
}
if(@$_GET['sta']=="odjava"){
	session_destroy();
	header("Location:index.php");
}
?>

    <body>
    <div class="postavka">
        <div class="body_omot">
        <div class="body_naslov">
            Ministarstvo
                  </div>

<center>
<?php

if(!isset($_SESSION['logovan'])){
	?>
 <table>
                <form action="admin.php" method="post">
           <tr><td><p> Korisnicko ime:</td><td><input type="text" name="kor_ime"></td></tr>
            <tr><td><p> Šifra:</td><td><input type="password" name="sifra"></td></tr></p>
            <tr><td align="left" colspan="2"><input type="submit" name="prijava" value="Prijavi se" /></td></tr>
                </form></table>
                <?php
}
else if($_SESSION['logovan']==true){
	echo "<h2>Dobro dosao ".$_SESSION['user'].". <a href='admin.php?sta=odjava'>Odjavi se</a></h2>";
	$baza=new baza();
	$con=$baza->konektujse();
	if(isset($_POST['dodaj_novost'])){
		$naslov=$_POST['naslov'];
		$opis=$_POST['opis'];
		$min=$_POST['ministarstvo'];
		$sql="insert into novosti values(NULL,'%s','%s',NOW(),'%s')";
		$res=mysql_query(sprintf($sql, mysql_real_escape_string($naslov), mysql_real_escape_string($opis),mysql_real_escape_string($min)));
		if($res)
			echo "Novost uspjesno dodana";
		else
		 	echo mysql_error(); 
		mysql_close($con);
	}
	if(@$_POST['vrij']==1){
		$stari=sha1($_POST['stari_pass']);
		$novi=$_POST['novi_pass'];
		$novi1=$_POST['novi_pass1'];
		if($novi==$novi1)
		{
			$sql="select * from korisnici where user='%s' and pass='%s'";
			$res=mysql_query(sprintf($sql, mysql_real_escape_string($_SESSION['user']), mysql_real_escape_string($stari)));
			if(mysql_num_rows($res)>0)
			{
				$pr="update korisnici set pass='%s' where id='%s'";
				$res1=mysql_query(sprintf($pr, mysql_real_escape_string(sha1($novi)), mysql_real_escape_string($_SESSION['id'])));
				if($res1)
					echo "Uspješno izmjenjena šifra";
				else
					echo mysql_error();
			}
			mysql_close($con);
		}
	}
	if(isset($_POST['dodaj_korisnika'])){
		$user=$_POST['username'];
		$pass=$_POST['pass'];
		$tip=$_POST['tip'];
		$sql="insert into korisnici values(NULL, '%s','%s','%s',1)";
		$res=mysql_query(sprintf($sql, mysql_real_escape_string($user), mysql_real_escape_string(sha1($pass)), mysql_real_escape_string($tip)));
		if($res)
			echo "Uspješno dodan korisnik";
		else
			echo mysql_error();
	}
	if(@$_GET['sta']=="izmjeni_korisnika"){
		?>
            <?php
		
		
	}
	if(isset($_POST['mijenjaj_korisnika'])){
		$id=$_POST['id'];
		$tip=$_POST['vrsta'];
		$sql="update korisnici set tip='%s' where id='%s'";
		$res=mysql_query(sprintf($sql,mysql_real_escape_string($tip),mysql_real_escape_string($id)));
		if($res)
			echo "Uspješno izmjenjeno";
		else
			mysql_error();
	}
	if(isset($_POST['brisi_korisnika'])){
		$id=$_POST['id'];
		if($_SESSION['id']==$id)
			echo "Ne mozete izbrisati sami sebe.";
		else {
			
			$sql="delete from korisnici where id='%s'";
			$res=mysql_query(sprintf($sql,mysql_real_escape_string($id)));
			if($res)
				echo "Uspješno izbrisan";
			else
				mysql_error();
		}
	}
	
	if($_SESSION['tip']=="SuperAdmin"){
            ?>
    <div id="izmjena"></div>
    <div class="novosti">
        
<h2> Dodavanje novog administratora</h2>
<a href="javascript:vidikorisnike();">Izmjeni postojeće korisnike</a>
	<table>
    <form action="admin.php" method="post">
    <tr><td>Username:</td><td><input type="text" name="username"></td></tr>
    <tr><td>Password:</td><td><input type="password" name="pass"></td></tr>
    <tr><td>Tip:</td><td><img src="images/question.jpg" width="20" onClick="opis()"><select name="tip"><option>Izabrati</option><option>SuperAdmin</option><option>Admin</option><option>Moderator</option></select></td></tr>
    <tr><td colspan="2"><input type="submit" name="dodaj_korisnika" value="Dodaj korisnika"></td></tr>
    </form>
    
    </table>
    </div>
    <?php
        }
		

	?>
     <div class="novosti">
    <h2 align="center">Dodavanje novosti</h2>
    <table>
    <form action="admin.php" method="post">
     <tr><td>Naslov:</td><td><input type="text" name="naslov"></td></tr>
    <tr><td>Opis:</td><td><textarea rows="5" cols="70" name="opis"></textarea></td></tr>
    <tr><td>Ministarstvo:</td><td><select name="ministarstvo"><option>Izabrati</option><option>Saobraćaj</option><option>Nauka</option><option>Sport</option></select></td></tr>
    <tr><td colspan="2"><input type="submit" name="dodaj_novost" value="Dodaj novost"></td></tr>
    </form>
    </table>
    </div>
     <div class="novosti">
    <h2 align="center">Promjena šifre</h2>
    <table>
    <form action="admin.php" method="post" name="promjena" id="promjena">
     <tr><td>Stari password:</td><td><input type="password" name="stari_pass"></td></tr>
    <tr><td>Novi password:</td><td><input type="password" name="novi_pass"></td></tr>
    <tr><td>Novi password (ponovo):</td><td><input type="password" name="novi_pass1"> <input type="hidden" name="vrij" value="0"></td></tr>
    <tr><td colspan="2"><input type="button" name="dodaj_novost" value="Promjeni sifru" onClick="promjenasifre()"></td></tr>
    </form>
    </table>
    </div>
    <?php
	
}
?>
                </center>
<div class="footer">Sva prava pridržana</div>
        </div>
    </body>
</html>