<?php
@session_start();
require_once("baza.php");
$baza=new baza();
if(isset($_SESSION['br_novosti'])) 
	{
		$_SESSION['br_novosti']=0;
		
	}
$con=$baza->konektujse();
$s="select * from novosti";
$r=mysql_query($s);
$i=0;
while($row=mysql_fetch_array($r)){
	$_SESSION['naziv'][$i]=$row['naziv'];
	$_SESSION['opis'][$i]=$row['opis'];
	$_SESSION['vrijeme'][$i]=$row['vrijeme'];
	$i++;
}
$_SESSION['br_novosti']=$i;
mysql_close($con);
?>