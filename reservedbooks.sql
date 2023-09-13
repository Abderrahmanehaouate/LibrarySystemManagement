-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 13 sep. 2023 à 17:49
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `librarydatabase`
--

-- --------------------------------------------------------

--
-- Structure de la table `reservedbooks`
--

CREATE TABLE `reservedbooks` (
  `ISBN` varchar(20) NOT NULL,
  `memberShip` varchar(50) NOT NULL,
  `reservationDate` date DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reservedbooks`
--

INSERT INTO `reservedbooks` (`ISBN`, `memberShip`, `reservationDate`) VALUES
('sakran', 'M2023091214570996', '2023-09-12'),
('welcomkdfj', 'M2023091314013134', '2023-09-13'),
('youcode', 'M2023091111392091', '2023-09-11');

--
-- Déclencheurs `reservedbooks`
--
DELIMITER $$
CREATE TRIGGER `ReserveBookTrigger` AFTER INSERT ON `reservedbooks` FOR EACH ROW BEGIN
    UPDATE books
    SET status = 'borrowed'
    WHERE ISBN = NEW.ISBN;
END
$$
DELIMITER ;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `reservedbooks`
--
ALTER TABLE `reservedbooks`
  ADD PRIMARY KEY (`ISBN`,`memberShip`),
  ADD KEY `reservedbooks_ibfk_2` (`memberShip`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `reservedbooks`
--
ALTER TABLE `reservedbooks`
  ADD CONSTRAINT `reservedbooks_ibfk_1` FOREIGN KEY (`ISBN`) REFERENCES `books` (`ISBN`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservedbooks_ibfk_2` FOREIGN KEY (`memberShip`) REFERENCES `clients` (`membership`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
