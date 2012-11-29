<?php
class baza{
	private $connect;
	private $host;
	private $user;
	private $pass;
	private $db;
	public function __construct(){
		$this->host="localhost";
		$this->user="root";
		$this->pass="niko";
		$this->db="nwt";
	}
	public function konektujse(){
		$this->connect=mysql_connect($this->host,$this->user,$this->pass);
		mysql_set_charset('utf8',$this->connect);
		mysql_select_db($this->db);
		return $this->connect;
	}
}
?>