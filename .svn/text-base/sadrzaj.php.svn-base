<?php  
if(@$_GET['stranica']=='pocetak' || @$_GET['stranica']==''){
	
	?>
     <div class="box">
        <img src="images/BIH.jpg"  width="250">
           
           <h1>Aktuelno</h1>
           <?php
		   include 'novosti.php';
		   for($i=0; $i<2; $i++)
		   {
			   if($_SESSION['br_novosti']<$i) break;
		   		echo "<h2>".$_SESSION['naziv'][$i]."</h2>";
		   }
		   ?>
            <h2><a href="admin.php" target="_blank">Administratorski panel</a></h2>
            <h2><a href="index.php?stranica=registracija">Registruj se</a></h2>
        </div>
            <div class="box1">
            <h1>Dobro došli</h1>
            <p>Dobro došli na oficijelnu web stranicu Ministarstva. Ovaj projekat će omogućiti ministarstvima svih nivoa (kantonalno, federalno...) jedinstvenost web stranice. </p>
            <p>Sa ove web stranice će se moći otvarati i pratiti dešavanja u svim ministarstvima. Ispod će se nalaziti loga raznih ministarstava. Sada su samo navedena 2 ispod.</p>
          <center><img src="images/monks.png"  width="250"></center><center><img src="images/zavod.png" alt="top" width="250"></center></div>
            
        </div>
    <?php
}




else if(@$_GET['stranica']=='novosti'){
	?>
      <div class="podloga">
     
          <h1>Novosti</h1>
           <?php
	   include 'novosti.php';
	   for($i=0; $i<$_SESSION['br_novosti'];$i++){
		   ?>
            <div class="novosti">
          	<h2><?php echo $_SESSION['naziv'][$i];?></h2>
          	<p><?php echo nl2br($_SESSION['opis'][$i]);?></p>
            <small><?php echo "Objavljeno: ".$_SESSION['vrijeme'][$i];?></small>
          	</div>
           <?php
	   }
	   ?>
         
         
        </div>
    <?php
}



else if(@$_GET['stranica']=='registracija'){
	?>
    <div class="podloga">
    <h1>Forma za registraciju</h1>
    <center>
    <p><?php if(isset($_SESSION['greska'])) {echo $_SESSION['greska']; session_unset($_SESSION['greska']);}?></p>
    <table align="center">
    <form action="index.php?stranica=registracija" method="post" name="registracija" id="registracija" onsubmit="return provjerisve()" >
    <tr><td width="50%">Ime:</td><td width="50%"><input type="text" name="ime" onblur="prime()" /><div id="imegr"></div></td></tr>
    <tr><td>Prezme:</td><td><input type="text" name="prezime" onblur="prprezime()" /><div id="prezimegr"></div></td></tr>
    <tr><td>Telefon:</td><td><input type="text" name="telefon" onblur="prtelefon()"/><div id="telefongr"></div></td></tr>
    <tr><td>JMBG:</td><td><input type="text" name="jmbg" onblur="prjmbg()" /><div id="jmbggr"></div></td></tr>
    <tr><td>E-mail:</td><td><input type="text" name="email" onblur="premail()" /><div id="emailgr"></div></td></tr>
    <tr><td>Adresa:</td><td><input type="text" name="adresa" onblur="pradresa()" /><div id="adresagr"></div></td></tr>
    <tr><td>Grad:</td><td><input type="text" name="grad" onblur="pradresa()" /><div id="adresagr"></div></td></tr>
    <tr><td>Datum rodjenja (YYYY-MM-DD):</td><td><input type="text" name="datum_rodj" onblur="prdatum()" /><div id="datumgr"></div></td></tr>
    <tr><td>Korisničko ime:</td><td><input type="text" name="username" id="username" onblur="provjeriusername()" /><div id="user"></div></td></tr>
    <tr><td>Šifra:</td><td><input type="password" name="pass" onkeyup="prpass()" /><div id="passgr"></div></td></tr>
    <tr><td>Potvrdi šifru:</td><td><input type="password" name="pass1" onblur="prpass1()" /><div id="passgr1"></div></td></tr>
    <tr><td colspan="2" align="right"><input type="submit" name="registracija" value="Registruj se" /></td></tr>
    </form>
    </table>
    </center>
    </div>
    <?php
}

else
	echo $_SESSION[$_GET['stranica']];




?>