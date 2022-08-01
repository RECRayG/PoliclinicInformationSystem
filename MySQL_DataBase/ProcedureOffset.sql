USE registry;

-- UPDATE timeofjob SET idTimeBegin = 22, idTimeEnd = 22 WHERE idWeek != 22 AND tableNumber > 1;

DROP TABLE IF EXISTS temp;
CREATE TABLE temp SELECT * FROM timeofjob;

DROP procedure IF EXISTS `offsetUp2`;
DELIMITER $$
USE `registry`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `offsetUp2`()
BEGIN

DECLARE done INT DEFAULT 1;
DECLARE idMaxWeek INT DEFAULT 21;
DECLARE currTimeBegin, currTimeEnd, currIdWeek, currTableNumber INT;
DECLARE cur CURSOR FOR SELECT idTimeBegin, idTimeEnd, idWeek, tableNumber FROM timeofjob WHERE idTimeOfJob >= 1;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 0;
OPEN cur;

FETCH NEXT FROM cur INTO currTimeBegin, currTimeEnd, currIdWeek, currTableNumber;
FETCH NEXT FROM cur INTO currTimeBegin, currTimeEnd, currIdWeek, currTableNumber;
WHILE done = 1 DO    
	IF currIdWeek <= idMaxWeek THEN 
		UPDATE temp SET idTimeBegin = currTimeBegin, idTimeEnd = currTimeEnd WHERE idWeek = currIdWeek - 1 AND tableNumber = currTableNumber;
        -- INSERT INTO temp (idTimeOfJob, tableNumber, idTimeBegin, idTimeEnd, idWeek) VALUES(currIdTimeOfJob - 1, currTableNumber, currTimeBegin, currTimeEnd, currIdWeek - 1) ON DUPLICATE KEY UPDATE idTimeBegin = currTimeBegin, idTimeEnd = currTimeEnd;
        FETCH NEXT FROM cur INTO currTimeBegin, currTimeEnd, currIdWeek, currTableNumber;
	ELSE
		UPDATE temp SET idTimeBegin = 22, idTimeEnd = 22 WHERE idWeek = idMaxWeek AND tableNumber = currTableNumber;
        -- INSERT INTO temp (idTimeOfJob, tableNumber, idTimeBegin, idTimeEnd, idWeek) VALUES(currIdTimeOfJob - 1, currTableNumber, currTimeBegin, currTimeEnd, idMaxWeek) ON DUPLICATE KEY UPDATE idTimeBegin = 22, idTimeEnd = 22;
        FETCH NEXT FROM cur INTO currTimeBegin, currTimeEnd, currIdWeek, currTableNumber;
        FETCH NEXT FROM cur INTO currTimeBegin, currTimeEnd, currIdWeek, currTableNumber;
	END IF;
END WHILE;

CLOSE cur;

UPDATE timeofjob tj, temp tmp SET tj.idTimeBegin = tmp.idTimeBegin, tj.idTimeEnd = tmp.idTimeEnd WHERE tj.idTimeOfJob = tmp.idTimeOfJob;

-- UPDATE timeofjob SET tj.idTimeBegin = tmp.idTimeBegin, tj.idTimeEnd = tmp.idTimeEnd 
-- FROM timerofjob tj INNER JOIN temp tmp 
-- ON tj.idTimeOfJob = tmp.idTimeOfJob;

END$$
DEALLOCATE cur;
DELIMITER ;

SET SQL_SAFE_UPDATES = 0;
CALL offsetUp2();
SET SQL_SAFE_UPDATES = 1;
-- SELECT * FROM temp;
SELECT * FROM timeofjob;