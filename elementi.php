<?php
require_once("baza.php");
$baza=new baza();
	$con=$baza->konektujse();
 if(@$_GET['korisnici']=="test"){
    
    
    
        echo "<div class='novosti'> <h2>Izmjeni postojeceg korisnika</h2><table>";
		$sql="select * from korisnici";
		$res=mysql_query($sql);
		while($row=mysql_fetch_array($res)){
			?>
            <form action="admin.php" method="post">
            <tr><td><?php echo $row['user'];?></td><td><select name="vrsta"><option><?php echo $row['tip'];?></option><option>SuperAdmin</option><option>Admin</option><option>Moderator</option></select></td><td><input type="hidden" name="id" value="<?php echo $row['id'];?>"><input type="submit" name="mijenjaj_korisnika" value="Mijenjaj korisnika"></td><td><input type="submit" name="brisi_korisnika" value="Brisi korisnika"></td></tr>
            </form>
        <?php
        echo "</table></div>";
                }


}
if(@$_GET['provjera']){
    $username=$_GET['provjera'];
    $sql="select * from clanovi where username='".$username."'";
    $res=  mysql_query($sql);
    if(mysql_num_rows($res)>0)
        echo "ima";
    else
        echo "nema";
}
?>
