-- ============================================================================
-- Teretana - DDL skripta za relacionu semu (8 tabela)
--
-- Izvor: dokumentacija, odeljak 3.5 (Relacioni model) i odeljak 3.6 (Tabela
-- strukturnih i vrednosnih ogranicenja relacionog modela). Nazivi tabela i
-- kolona, tipovi atributa i strukturna (FK) ogranicenja su preuzeti odatle
-- doslovno - ne izmisljeni.
--
-- NAMERNO IZOSTAVLJENO: proste i slozene VREDNOSNE provere (npr. "cena>0",
-- "idTrener>0", "datumVremeZavrsetka > datumVremePocetka", agregatna provera
-- COUNT(Clanarina)<=maxBrojUcesnika za TerminTreninga, unakrsna provera
-- datumTreninga >= Trener.datumZaposlenja za VodjenjeTreninga) NISU
-- implementirane ovde kao CHECK ogranicenja, iako bi MySQL 8 to podrzao.
-- Ovo je svesna odluka po handoff dokumentu, sekcija 7.5: poslovna/vrednosna
-- ogranicenja se proveravaju u validate() na Java strani (AbstractSystemOperation),
-- dok se u samoj bazi implementira ISKLJUCIVO referencijalni integritet
-- (FK, ON UPDATE CASCADE, ON DELETE RESTRICT). Kontradiktorno/agregatno
-- ogranicenje za TerminTreninga (dokumentacija, tacka 3.6) ostaje
-- nedirano i neimplementirano na bilo kom nivou, po zakljucanoj odluci 10.2.
--
-- Tipovi kolona koje dokumentacija ne precizira eksplicitno (duzine VARCHAR
-- polja, osim Clan.email koje ima dokumentovano ogranicenje <=50 karaktera)
-- su tehnicki izbor (VARCHAR(255) kao podrazumevana duzina za "String"
-- atribute), ne poslovno pravilo.
--
-- Redosled kreiranja tabela prati redosled iz sekcije 5 handoff dokumenta
-- (bez FK zavisnosti prve, pa redom po zavisnostima).
-- ============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS VodjenjeTreninga;
DROP TABLE IF EXISTS TerminTreninga;
DROP TABLE IF EXISTS Clanarina;
DROP TABLE IF EXISTS Clan;
DROP TABLE IF EXISTS ProgramTreninga;
DROP TABLE IF EXISTS KategorijaClanarine;
DROP TABLE IF EXISTS TipTreninga;
DROP TABLE IF EXISTS Trener;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------------------------------------------------------
-- 1. Trener  (dokumentacija 3.5, red 1: Trener(idTrener, ime, prezime,
--    korisnickoIme, sifra, datumZaposlenja))
-- ----------------------------------------------------------------------------
CREATE TABLE Trener (
    idTrener        INT UNSIGNED    NOT NULL AUTO_INCREMENT,
    ime             VARCHAR(255)    NOT NULL,
    prezime         VARCHAR(255)    NOT NULL,
    korisnickoIme   VARCHAR(255)    NOT NULL,
    sifra           VARCHAR(255)    NOT NULL,
    datumZaposlenja DATE            NOT NULL,
    PRIMARY KEY (idTrener)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 2. TipTreninga  (dokumentacija 3.5, red 2: TipTreninga(idTipTreninga, naziv,
--    nivoTezine))
-- ----------------------------------------------------------------------------
CREATE TABLE TipTreninga (
    idTipTreninga   INT UNSIGNED    NOT NULL AUTO_INCREMENT,
    naziv           VARCHAR(255)    NOT NULL,
    nivoTezine      VARCHAR(255)    NOT NULL,
    PRIMARY KEY (idTipTreninga)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 3. KategorijaClanarine  (dokumentacija 3.5, red 3: KategorijaClanarine(
--    idKategorijaClanarine, naziv))
-- ----------------------------------------------------------------------------
CREATE TABLE KategorijaClanarine (
    idKategorijaClanarine  INT UNSIGNED    NOT NULL AUTO_INCREMENT,
    naziv                  VARCHAR(255)    NOT NULL,
    PRIMARY KEY (idKategorijaClanarine)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 4. ProgramTreninga  (dokumentacija 3.5, red 4: ProgramTreninga(
--    idProgramTreninga, naziv, opis, brojNedelja))
-- ----------------------------------------------------------------------------
CREATE TABLE ProgramTreninga (
    idProgramTreninga  INT UNSIGNED    NOT NULL AUTO_INCREMENT,
    naziv              VARCHAR(255)    NOT NULL,
    opis               VARCHAR(255)    NOT NULL,
    brojNedelja        INT UNSIGNED    NOT NULL,
    PRIMARY KEY (idProgramTreninga)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 5. Clan  (dokumentacija 3.5, red 5: Clan(idClan, ime, prezime, email,
--    datumUclanjenja, aktivan, idKategorijaClanarine))
--    FK idKategorijaClanarine -> KategorijaClanarine: ON UPDATE CASCADE,
--    ON DELETE RESTRICT (dokumentacija 3.6, tabela KategorijaClanarine:
--    "UPDATE CASCADES -> Clan", "DELETE RESTRICTED -> Clan")
-- ----------------------------------------------------------------------------
CREATE TABLE Clan (
    idClan                  INT UNSIGNED    NOT NULL AUTO_INCREMENT,
    ime                     VARCHAR(255)    NOT NULL,
    prezime                 VARCHAR(255)    NOT NULL,
    email                   VARCHAR(50)     NOT NULL,
    datumUclanjenja         DATE            NOT NULL,
    aktivan                 BOOLEAN         NOT NULL,
    idKategorijaClanarine   INT UNSIGNED    NOT NULL,
    PRIMARY KEY (idClan),
    CONSTRAINT fk_Clan_KategorijaClanarine
        FOREIGN KEY (idKategorijaClanarine) REFERENCES KategorijaClanarine (idKategorijaClanarine)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 6. Clanarina  (dokumentacija 3.5, red 6: Clanarina(idClanarina, datumPocetka,
--    datumZavrsetka, cena, status, idTrener, idClan))
--    FK idTrener -> Trener: ON UPDATE CASCADE, ON DELETE RESTRICT
--      (dokumentacija 3.6, tabela Trener: "UPDATE CASCADES -> Clanarina",
--      "DELETE RESTRICTED -> Clanarina")
--    FK idClan -> Clan: ON UPDATE CASCADE, ON DELETE RESTRICT
--      (dokumentacija 3.6, tabela Clan: "UPDATE CASCADES -> Clanarina",
--      "DELETE RESTRICTED -> Clanarina")
-- ----------------------------------------------------------------------------
CREATE TABLE Clanarina (
    idClanarina     INT UNSIGNED    NOT NULL AUTO_INCREMENT,
    datumPocetka    DATE            NOT NULL,
    datumZavrsetka  DATE            NOT NULL,
    cena            DOUBLE          NOT NULL,
    status          VARCHAR(255)    NOT NULL,
    idTrener        INT UNSIGNED    NOT NULL,
    idClan          INT UNSIGNED    NOT NULL,
    PRIMARY KEY (idClanarina),
    CONSTRAINT fk_Clanarina_Trener
        FOREIGN KEY (idTrener) REFERENCES Trener (idTrener)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_Clanarina_Clan
        FOREIGN KEY (idClan) REFERENCES Clan (idClan)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 7. TerminTreninga  (dokumentacija 3.5, red 7: TerminTreninga(idClanarina,
--    idTerminTreninga, sala, datumVremePocetka, datumVremeZavrsetka,
--    maxBrojUcesnika, idTipTreninga))
--    Napomena (zakljucana odluka 10.1): koncept jos uvek nema sistemske
--    operacije ni GUI formu - tabela postoji samo kao deo relacionog modela.
--    FK idClanarina -> Clanarina: ON UPDATE CASCADE, ON DELETE RESTRICT
--      (dokumentacija 3.6, tabela Clanarina: "UPDATE CASCADES -> TerminTreninga",
--      "DELETE RESTRICTED -> TerminTreninga")
--    FK idTipTreninga -> TipTreninga: ON UPDATE CASCADE, ON DELETE RESTRICT
--      (dokumentacija 3.6, tabela TipTreninga: "UPDATE CASCADES -> TerminTreninga",
--      "DELETE RESTRICTED -> TerminTreninga")
-- ----------------------------------------------------------------------------
CREATE TABLE TerminTreninga (
    idTerminTreninga     INT UNSIGNED    NOT NULL AUTO_INCREMENT,
    sala                 VARCHAR(255)    NOT NULL,
    datumVremePocetka    DATETIME        NOT NULL,
    datumVremeZavrsetka  DATETIME        NOT NULL,
    maxBrojUcesnika      INT UNSIGNED    NOT NULL,
    idClanarina          INT UNSIGNED    NOT NULL,
    idTipTreninga        INT UNSIGNED    NOT NULL,
    PRIMARY KEY (idTerminTreninga),
    CONSTRAINT fk_TerminTreninga_Clanarina
        FOREIGN KEY (idClanarina) REFERENCES Clanarina (idClanarina)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_TerminTreninga_TipTreninga
        FOREIGN KEY (idTipTreninga) REFERENCES TipTreninga (idTipTreninga)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------------------------------------------------------
-- 8. VodjenjeTreninga  (dokumentacija 3.5, red 8: VodjenjeTreninga(idTrener,
--    idProgramTreninga, datumTreninga))
--    Napomena (zakljucana odluka 10.1): koncept jos uvek nema sistemske
--    operacije ni GUI formu - tabela postoji samo kao deo relacionog modela.
--    Nema sopstveni surogatni PK - dokumentovani identitet je slozeni kljuc
--    (idTrener, idProgramTreninga), sto se ovde doslovno prenosi kao
--    kompozitni PRIMARY KEY (u skladu sa VodjenjeTreninga.getId()/setId()
--    koji namerno bacaju UnsupportedOperationException - videti domensku
--    klasu).
--    FK idTrener -> Trener: ON UPDATE CASCADE, ON DELETE RESTRICT
--      (dokumentacija 3.6, tabela Trener: "UPDATE CASCADES -> VodjenjeTreninga",
--      "DELETE RESTRICTED -> VodjenjeTreninga")
--    FK idProgramTreninga -> ProgramTreninga: ON UPDATE CASCADE, ON DELETE RESTRICT
--      (dokumentacija 3.6, tabela ProgramTreninga: "UPDATE CASCADES -> VodjenjeTreninga",
--      "DELETE RESTRICTED -> VodjenjeTreninga")
-- ----------------------------------------------------------------------------
CREATE TABLE VodjenjeTreninga (
    idTrener            INT UNSIGNED    NOT NULL,
    idProgramTreninga   INT UNSIGNED    NOT NULL,
    datumTreninga       DATE            NOT NULL,
    PRIMARY KEY (idTrener, idProgramTreninga),
    CONSTRAINT fk_VodjenjeTreninga_Trener
        FOREIGN KEY (idTrener) REFERENCES Trener (idTrener)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_VodjenjeTreninga_ProgramTreninga
        FOREIGN KEY (idProgramTreninga) REFERENCES ProgramTreninga (idProgramTreninga)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
