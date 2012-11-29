<?php
session_start();
require_once("baza.php");
$baza=new baza();
if(!isset($_SESSION['sadrzaj'])){
	$con=$baza->konektujse();
	$s="select * from sadrzaj";
	$r=mysql_query($s);
	$i=0;
	while($row=mysql_fetch_array($r)){
		if($row['pozicija']!="superadmin" && $row['pozicija']!="admin_all")
			$_SESSION[$row['pozicija']]=$row['kod'];
		
	}
}
mysql_close($con);
if(@$_POST['registracija']){
	$ime=$_POST['ime'];
	$prezime=$_POST['prezime'];
	$telefon=$_POST['telefon'];
	$jmbg=$_POST['jmbg'];
	$mail=$_POST['email'];
	$adresa=$_POST['adresa'];
	$grad=$_POST['grad'];
	$datum=$_POST['datum_rodj'];
	$user=$_POST['username'];
	$pass=$_POST['pass'];
	$pass1=$_POST['pass1'];
	$greska=0;
	if(strlen($ime)<3 || strlen($prezime)<3){
		$greska++;
		$_SESSION['greska']="Ime ili prezime prekratko.";
	}
	if(strlen($user)<3 || strlen($pass)<3 ){
		$greska++;
		$_SESSION['greska']="Korisničko ime ili šifra nema dovoljno karaktera.";
	}
	if($pass!=$pass1){
		$greska++;
		$_SESSION['greska']="Sifre nisu jednake.";
	}
	$tel=explode("[/-]", $telefon);
	$provjera=0;
	foreach($tel as $t){
		if(!ctype_digit($t))
			$provjera++;
	}
	if((!ctype_digit($telefon) && sizeof($tel)<2) || $provjera>0){
		$greska++;
		$_SESSION['greska']="Telefon nije u ispravnom formatu. Dozvoljeni formati su: 061000000 ili 061/000-000";
	}
	if(!ctype_digit($jmbg)){
		$greska++;
		$_SESSION['greska']="JMBG smije da sadrzi samo cifre";
	}
	$provjera=0;
	$email=explode("@", $mail);
	foreach($email as $m){
		if(strlen($m)<3)
			$provjera++;
	}
	if(sizeof($email)<2 || $provjera>0){
		$greska++;
		$_SESSION['greska']="Neispravan formati mail-a";
	}
	$f=explode("-", $datum);
	$provjera=0;
	foreach($f as $d){
		if($d>1994){
			$greska++;
			$_SESSION['greska']="Morate biti punoljetni";
		}
		if(!ctype_digit($d)){
			$greska++;
			$_SESSION['greska']="Datum mora biti formata: YYYY-MM-DD";
		}
		
	}
	if(strlen($adresa)<3 || strlen($grad)<3){
		$greska++;
		$_SESSION['greska']="Adresa ili grad prekratki.";
	}
	if($greska==0){
		require_once("baza.php");
		$baza=new baza();
		$con=$baza->konektujse();
		$sql="insert into clanovi values(NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', NOW(), '%s','%s')";
		$res=mysql_query(sprintf($sql,mysql_real_escape_string($ime), mysql_real_escape_string($prezime) ,mysql_real_escape_string($telefon) , mysql_real_escape_string($jmbg), mysql_real_escape_string($mail), mysql_real_escape_string($adresa), mysql_real_escape_string($grad), mysql_real_escape_string($datum), mysql_real_escape_string($user), mysql_real_escape_string(sha1($pass))));
		if($res)
			echo "Uspješno ste se reqistrovali!";
		else
			echo mysql_error();
		mysql_close($con);
	}
}
?>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ministarstvo</title>
        <link href="css/stil.css" rel="stylesheet" type="text/css" />
         <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript">
		function isDigit(aChar)
   {
      myCharCode = aChar.charCodeAt(0);
   
      if((myCharCode > 47) && (myCharCode <  58))
      {
         return true;
      }
   
      return false;
   }
		function prtelefon(){
			var greska=document.getElementById('telefongr');
			var forma=document.forms.registracija;
		 var telefonRegEx = /^\(?(\d{3})\)?[-]?(\d{3})[-]?(\d{3})$/;
			if (!telefonRegEx.test(forma['telefon'].value)) {
				greska.innerHTML="Telefon format: (061)-123-345 ili 061-123-456 ili 061123456<br>"; 
				forma.telefon.style.background="red";
				return false;
			}
			else{ forma.telefon.style.background="white"; greska.innerHTML="";}
		}
		function prime(){
			var greska=document.getElementById('imegr');
			var forma=document.forms.registracija;
		 if (forma.ime.value.length>10 || forma.ime.value.length<3) {
                                greska.innerHTML="Predugo/prekratko ime<br>"; forma.ime.style.background="red";  return false; }
else {forma.ime.style.background="white"; greska.innerHTML="";
}
		}
		
		function prprezime(){
			var greska=document.getElementById('prezimegr');
			var forma=document.forms.registracija;
		 if (forma.prezime.value.length>10 || forma.prezime.value.length<3) {
                                greska.innerHTML="Predugo/prekratko prezime<br>"; forma.prezime.style.background="red";  return false; }
else {forma.prezime.style.background="white"; greska.innerHTML="";
}
		}
		function pradresa(){
			var greska=document.getElementById('adresagr');
			var forma=document.forms.registracija;
		 if (forma.adresa.value.length<3 || forma.grad.value.length<3) {
                                greska.innerHTML="Niste unijeli ime grada ili adresu. Min 3 karaktera."; forma.adresa.style.background="red";  return false; }
else {forma.adresa.style.background="white"; greska.innerHTML="";
}
		}
		function prjmbg(){
			var greska=document.getElementById('jmbggr');
			var forma=document.forms.registracija;
			var jm=forma.jmbg.value;
		for(i=0;i <forma.jmbg.value.length; i++){
			if(!isDigit(jm[i])){
				 greska.innerHTML="Mogu samo brojevi<br>"; forma.jmbg.style.background="red";  return false;
			}
			else {forma.jmbg.style.background="white"; greska.innerHTML="";
}
		}
		if(forma.jmbg.value.length<13 || forma.jmbg.value.length>13){
				 greska.innerHTML="JMBG mora imati 13 karaktera."; forma.jmbg.style.background="red";  return false;
			}
			else {forma.jmbg.style.background="white"; greska.innerHTML="";
}

		}
		function premail(){
			var greska=document.getElementById('emailgr');
			var forma=document.forms.registracija;
					 var validiraj=forma.email.value.split("@");
			if(validiraj.length>2){
		greska.innerHTML+="Ima vise @ u mailu.<br>"; forma.email.style.background="red"; return false; }
		else {forma.email.style.background="white"; greska.innerHTML="";
		}
			if(validiraj.length<2){
			greska.innerHTML+="Nema @ u mailu.<br>"; forma.email.style.background="red";  return false; }
		else {forma.email.style.background="white"; greska.innerHTML="";
		}
		
			if(validiraj[0].length<2 || validiraj[1].length<2){
		greska.innerHTML+="Ima manje od 2 karaktera<br>"; forma.email.style.background="red"; return false; }
		else {forma.email.style.background="white"; greska.innerHTML="";
		}
		}
		
                
                function provjeriusername(){
                    var user=$('#username').val();
                    var odgovor="";
                   
                     jQuery.get("elementi.php?provjera="+user, function(data){
                         
                        odgovor=data.toString();
  
                     if(odgovor=="ima"){
                        
                        jQuery('#user').html("Korisničko ime već postoji");
                        return false;
                    }
                    else{
                        jQuery('#user').html("");
                        return true;
                    }
                        
                    });
                   
                }
		function prdatum(){
			var greska=document.getElementById('datumgr');
			var forma=document.forms.registracija;
			var validiraj=forma.email.value.split("-");
			if(validiraj.length>3 || validiraj.length<1 ){
				greska.innerHTML="Neispravan format datuma. Format mora biti YYYY-MM-DD"; forma.datum_rodj.style.background="red"; return false; }
				else {forma.datum_rodj.style.background="white"; greska.innerHTML="";
			}
		}
		
		function prpass(){
			var greska=document.getElementById('passgr');
			var forma=document.forms.registracija;
			var validiraj=forma.email.value.split("-");
			var brojac=0;
			var brojac1=0;
			for(i=0;i <forma.pass.value.length; i++){
				if(isDigit(forma.pass.value[i])){
					brojac++;
				}
				if(forma.pass.value[i]==forma.pass.value[i].toUpperCase() && !isDigit(forma.pass.value[i])){
					
					brojac1++;
				}
			}
			 if (forma.pass.value.length<6) {
                                greska.innerHTML="Sifra ima manje od 6 karaktera"; forma.pass.style.background="red";  return false; }
			else if(brojac==0){
				greska.innerHTML="Sifra mora imati bar jedan broj"; forma.pass.style.background="red";  return false;
			}
			else if(brojac1==0){
				greska.innerHTML="Sifra mora imati bar jedno veliko slovo."; forma.pass.style.background="red";  return false;
			}
else {forma.pass.style.background="green"; greska.innerHTML="";
}
		}
		
		function prpass1(){
			var greska=document.getElementById('passgr1');
			var forma=document.forms.registracija;
			if(forma.pass.value!=forma.pass1.value){
				greska.innerHTML="Nisu jednake sifre."; forma.pass1.style.background="red";  return false;
			}
			else {forma.pass1.style.background="green"; greska.innerHTML="";
}
		}
		
		function provjerisve(){
			if(prime()==false  || prprezime()==false || prtelefon()==false || premail()==false || pradresa()==false || prjmbg()==false || prdatum()==false || prpass()==false || prpass1()==false)
				return false;
			
		}
    </script>
    </head>
    <body>
    <div class="postavka">
        <div class="body_omot">
        <div class="body_naslov">
            Ministarstvo
                  </div>
        <div id="menu_wrapper" class="blue">
		<div class="left"></div>
			<ul id="menu">
				<li <?php if(@$_GET['stranica']=='pocetak') echo 'class="active"';?>><a href="index.php?stranica=pocetak">Početna</a></li>
				<li <?php if(@$_GET['stranica']=='onama') echo 'class="active"';?>><a href="index.php?stranica=onama">O ministarstvu</a></li>
				<li  <?php if(@$_GET['stranica']=='ministarstva') echo 'class="active"';?>><a href="index.php?stranica=ministarstva">Ministarstva</a></li>
				<li <?php if(@$_GET['stranica']=='novosti') echo 'class="active"';?>><a href="index.php?stranica=novosti">Novosti</a></li>
				<li <?php if(@$_GET['stranica']=='kontakt') echo 'class="active"';?>><a href="index.php?stranica=kontakt">Kontakt</a></li>
			</ul>
		</div>
        <?php include 'sadrzaj.php';?>
       
        <div class="footer">Sva prava pridržana</div>
        </div>
    </body>
</html>
