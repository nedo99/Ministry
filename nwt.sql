-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 29, 2012 at 03:21 PM
-- Server version: 5.1.36
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `nwt`
--

-- --------------------------------------------------------

--
-- Table structure for table `clanovi`
--

CREATE TABLE IF NOT EXISTS `clanovi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(20) COLLATE utf8_slovenian_ci NOT NULL,
  `prezime` varchar(20) COLLATE utf8_slovenian_ci NOT NULL,
  `telefon` varchar(20) COLLATE utf8_slovenian_ci NOT NULL,
  `jmbg` varchar(13) COLLATE utf8_slovenian_ci NOT NULL,
  `mail` varchar(20) COLLATE utf8_slovenian_ci NOT NULL,
  `adresa` varchar(40) COLLATE utf8_slovenian_ci NOT NULL,
  `grad` varchar(30) COLLATE utf8_slovenian_ci NOT NULL,
  `datum_rodjenja` date NOT NULL,
  `datum_registracije` date NOT NULL,
  `username` varchar(20) COLLATE utf8_slovenian_ci NOT NULL,
  `pass` varchar(30) COLLATE utf8_slovenian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `clanovi`
--

INSERT INTO `clanovi` (`id`, `ime`, `prezime`, `telefon`, `jmbg`, `mail`, `adresa`, `grad`, `datum_rodjenja`, `datum_registracije`, `username`, `pass`) VALUES
(1, 'Nedim', 'Hadzic', '061575740', '1205989190037', 'nedimhadzija@gmail.c', 'Gradačačka 31', 'Sarajevo', '1989-05-12', '2012-03-26', 'nedo99', 'volimburek');

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE IF NOT EXISTS `korisnici` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(20) COLLATE ucs2_slovenian_ci NOT NULL,
  `pass` varchar(50) COLLATE ucs2_slovenian_ci NOT NULL,
  `tip` varchar(20) COLLATE ucs2_slovenian_ci NOT NULL,
  `validan` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=ucs2 COLLATE=ucs2_slovenian_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`id`, `user`, `pass`, `tip`, `validan`) VALUES
(1, 'nedim', '3832447542e8ad86054c3985c1ee97938b42fc5b', 'SuperAdmin', 1);

-- --------------------------------------------------------

--
-- Table structure for table `novosti`
--

CREATE TABLE IF NOT EXISTS `novosti` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(200) COLLATE utf8_slovenian_ci NOT NULL,
  `opis` text COLLATE utf8_slovenian_ci NOT NULL,
  `vrijeme` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ministarstvo` varchar(30) COLLATE utf8_slovenian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci AUTO_INCREMENT=5 ;

--
-- Dumping data for table `novosti`
--

INSERT INTO `novosti` (`id`, `naziv`, `opis`, `vrijeme`, `ministarstvo`) VALUES
(3, 'Test', 'Kako ide ovosekfhdskh jd\r\ndfgd\r\n', '2012-03-20 13:14:27', 'Saobra?aj'),
(4, 'Projektni zadatak 3', 'Omogućio sam da postoji mogućnost izmjene korisnika, dodavanje novosti i mijenjanja sifre za korisnike u administratorskom panelu, koji se otvara sa home page-a kliknom na "administracijski panel. \r\nOpcija dodavanja ministarstava ce biti omogucena u sljedecem projektom zadatku. ', '2012-03-20 19:20:08', 'Nauka');

-- --------------------------------------------------------

--
-- Table structure for table `sadrzaj`
--

CREATE TABLE IF NOT EXISTS `sadrzaj` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kod` text CHARACTER SET ucs2 COLLATE ucs2_slovenian_ci NOT NULL,
  `validan` int(11) NOT NULL,
  `pozicija` varchar(30) COLLATE utf8_slovenian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_slovenian_ci AUTO_INCREMENT=7 ;

--
-- Dumping data for table `sadrzaj`
--

INSERT INTO `sadrzaj` (`id`, `kod`, `validan`, `pozicija`) VALUES
(1, ' <div class="novosti">\r\n    <h2 align="center">Dodavanje novosti</h2>\r\n    <table>\r\n    <form action="admin.php" method="post">\r\n     <tr><td>Naslov:</td><td><input type="text" name="naslov"></td></tr>\r\n    <tr><td>Opis:</td><td><textarea rows="5" cols="70" name="opis"></textarea></td></tr>\r\n    <tr><td>Ministarstvo:</td><td><select name="ministarstvo"><option>Izabrati</option><option>Saobraćaj</option><option>Nauka</option><option>Sport</option></select></td></tr>\r\n    <tr><td colspan="2"><input type="submit" name="dodaj_novost" value="Dodaj novost"></td></tr>\r\n    </form>\r\n    </table>\r\n    </div>\r\n     <div class="novosti">\r\n    <h2 align="center">Promjena šifre</h2>\r\n    <table>\r\n    <form action="admin.php" method="post" name="promjena" id="promjena">\r\n     <tr><td>Stari password:</td><td><input type="password" name="stari_pass"></td></tr>\r\n    <tr><td>Novi password:</td><td><input type="password" name="novi_pass"></td></tr>\r\n    <tr><td>Novi password (ponovo):</td><td><input type="password" name="novi_pass1"> <input type="hidden" name="vrij" value="0"></td></tr>\r\n    <tr><td colspan="2"><input type="button" name="dodaj_novost" value="Promjeni sifru" onClick="promjenasifre()"></td></tr>\r\n    </form>\r\n    </table>\r\n    </div>', 1, 'admin_all'),
(2, '<div class="novosti">\r\n<h2> Dodavanje novog administratora</h2>\r\n<a href="admin.php?sta=izmjeni_korisnika">Izmjeni postojeće korisnike</a>\r\n	<table>\r\n    <form action="admin.php" method="post">\r\n    <tr><td>Username:</td><td><input type="text" name="username"></td></tr>\r\n    <tr><td>Password:</td><td><input type="password" name="pass"></td></tr>\r\n    <tr><td>Tip:</td><td><img src="images/question.jpg" width="20" onClick="opis()"><select name="tip"><option>Izabrati</option><option>SuperAdmin</option><option>Admin</option><option>Moderator</option></select></td></tr>\r\n    <tr><td colspan="2"><input type="submit" name="dodaj_korisnika" value="Dodaj korisnika"></td></tr>\r\n    </form>\r\n    \r\n    </table>\r\n    </div>', 1, 'superadmin'),
(3, ' <div class="podloga">\r\n            <h1>O nama</h1>\r\n            \r\n            <p>Web stranica Ministarstva će da omogući redovno obavještavanje o radu kompletnoh ministarstva.</p>\r\n            <p>Web stranica Ministarstva će omogućiti:</p>\r\n            <ul>\r\n            <li>dodavanje novih ministarstava koje će imati link domena.com/imeministarstva</li>\r\n            <li>dodavanje novih korisnika za odgovarajuca ministarstva</li>\r\n            <li>svaki kosrisnik ministarstva će imati opciju da dodaju novosti</li>\r\n            <li>korisnik će imati opciju da dodaje konkurse, pozive za finansiranje itd</li>\r\n            <li>kandidati, koji zele aplicirati na gore spomenute konkurse, će to moći uraditi online</li>\r\n        </ul>\r\n            </div>\r\n        </div>', 1, 'onama'),
(4, '  <div class="podloga">\r\n            <h1><center>Ministarstva</center></h1>\r\n            <p align="center">Dolje navedeni linkovi ne rade i ovo je samo simulacija. Kao što je već spomenuto, ministarstvo kada doda neko novo ministarstvo, link za to novo ministarstvo će se pojaviti na ovoj listi linkova.\r\n           \r\n           <h2><center><a href="#">Saobraćaj</a></center></h2>\r\n           <h2><center><a href="#">Nauka i sport</a></center></h2>\r\n           <h2><center><a href="#">Kultura</a></center></h2>\r\n           \r\n            </div>\r\n        </div>', 1, 'ministarstva'),
(5, ' <div class="box">\r\n        <img src="images/BIH.jpg"  width="250">\r\n           \r\n           <h1>Aktuelno</h1>\r\n           <?php\r\n		   include ''novosti.php'';\r\n		   for($i=0; $i<2; $i++)\r\n		   	echo "<h2>".$_SESSION[$i][''naziv'']."</h2>";\r\n		   ?>\r\n            <h2><a href="admin.php" target="_blank">Administratorski panel</a></h2>\r\n        </div>\r\n            <div class="box1">\r\n            <h1>Dobro došli</h1>\r\n            <p>Dobro došli na oficijelnu web stranicu Ministarstva. Ovaj projekat će omogućiti ministarstvima svih nivoa (kantonalno, federalno...) jedinstvenost web stranice. </p>\r\n            <p>Sa ove web stranice će se moći otvarati i pratiti dešavanja u svim ministarstvima. Ispod će se nalaziti loga raznih ministarstava. Sada su samo navedena 2 ispod.</p>\r\n          <center><img src="images/monks.png"  width="250"></center><center><img src="images/zavod.png" alt="top" width="250"></center></div>\r\n            \r\n        </div>', 1, 'pocetak'),
(6, ' <div class="podloga">\r\n            <h1>Kontakt</h1>\r\n            <p >\r\n            <ul>\r\n            <li>Adresa</li>\r\n            <li>Grad</li>\r\n            <li>Telefon:000/000-000</li>\r\n            <li>mail: </li>\r\n            </ul>\r\n            </p>\r\n           \r\n            </div>\r\n        </div>', 1, 'kontakt');
