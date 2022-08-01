USE registry;
-- SELECT secondName, firstName, thirdName, doc.tableNumber, pl.numberOfPlot, addPl.street, addPl.buildingCount, addPl.idAddress FROM doctors doc, plots pl, addressPlot addPl WHERE doc.tableNumber = pl.tableNumber AND addPl.numberOfPlot = pl.numberOfPlot;
-- SELECT secondName, firstName, thirdName, doc.tableNumber, pl.numberOfPlot, addPl.street, addPl.buildingCount, addPl.idAddress 
-- FROM doctors doc INNER JOIN plots pl INNER JOIN addressPlot addPl 
-- ON doc.tableNumber = pl.tableNumber
-- WHERE addPl.numberOfPlot = pl.numberOfPlot;

-- SELECT secondName, firstName, thirdName, sp.specialization, doc.tableNumber, pl.numberOfPlot, addPl.street, addPl.buildingCount, addPl.idAddress 
-- FROM doctors doc INNER JOIN specialization sp JOIN plots pl INNER JOIN addressPlot addPl 
-- ON doc.tableNumber = pl.tableNumber
-- WHERE addPl.numberOfPlot = pl.numberOfPlot AND doc.specializationCode = sp.specializationCode AND sp.specialization = 'терапевт';

-- DELETE doctors, plots FROM doctors INNER JOIN plots 
-- WHERE doctors.tableNumber = 11 AND plots.tableNumber = 11;

-- INSERT INTO weeks SET dateWeeks = DATE(DATE_ADD(NOW(), INTERVAL 3 DAY));
-- INSERT INTO weeks SET dateWeeks = DATE(DATE_ADD(NOW(), INTERVAL 0 DAY)), idDay = DAYOFWEEK(DATE(dateWeeks)) - 1;

-- SELECT t.Hours, ti.Hours, tj.idWeek
-- FROM timeofjob tj INNER JOIN time t INNER JOIN time ti INNER JOIN weeks w
-- ON tj.idWeek = w.idWeek 
-- AND t.idTime = tj.idTimeBegin 
-- AND ti.idTime = tj.idTimeEnd
-- AND tj.tableNumber = '1';



-- DROP TABLE IF EXISTS temp;
-- CREATE TABLE temp SELECT * FROM timeofjob;

-- DROP procedure IF EXISTS `offsetUp2`;
-- DELIMITER $$
-- USE `registry`$$
-- CREATE DEFINER=`root`@`localhost` PROCEDURE `offsetUp2`()
-- BEGIN

-- DECLARE idCurrentWeek INT DEFAULT 1;
-- DECLARE idMaxWeek INT DEFAULT 21;
-- DECLARE currTimeBegin, currTimeEnd INT;
-- DECLARE cur CURSOR FOR SELECT idTimeBegin, idTimeEnd FROM timeofjob WHERE idTimeOfJob >= 1;

-- OPEN cur;

-- FETCH NEXT FROM cur INTO currTimeBegin, currTimeEnd;

-- WHILE @@FETCH_STATUS DO
  -- IF idCurrentWeek < idMaxWeek THEN 
	-- INSERT INTO temp SET idTimeBegin = prevTimeBegin, idTimeEnd = prevTimeEnd ON DUPLICATE KEY UPDATE idTimeOfJob = IdTimeOfJob;
    -- UPDATE temp SET idTimeBegin = prevTimeBegin, idTimeEnd = prevTimeEnd WHERE idWeek = idCurrentWeek AND idTimeOfJob = currIdTimeOfJob;
    -- SET idCurrentWeek = idCurrentWeek + 1;
   -- ELSE
	-- INSERT INTO temp SET idTimeBegin = prevTimeBegin, idTimeEnd = prevTimeEnd ON DUPLICATE KEY UPDATE idTimeOfJob = IdTimeOfJob;
    -- UPDATE temp SET idTimeBegin = 22, idTimeEnd = 22 WHERE idWeek = idCurrentWeek AND idTimeOfJob = currIdTimeOfJob;
    -- SET idCurrentWeek = 1;
  -- END IF;
  -- SET prevTimeBegin = currTimeBegin;
  -- SET prevTimeEnd = currTimeEnd;
  -- SET prevIdTimeOfJob = currIdTimeOfJob;
  -- FETCH cur INTO currIdTimeOfJob, idCurrentWeek, currTimeBegin, currTimeEnd;
-- END WHILE;

-- CLOSE cur;

-- END$$

-- DELIMITER ;

-- CALL offsetUp();

-- SET SQL_SAFE_UPDATES = 0;

SELECT CONCAT(doc.secondName, ' ' , doc.firstName, ' ', doc.thirdName), sp.specialization, tt.numberOfCabinet, d.Day, t.Hours, ti.Hours, w.dateWeeks, doc.tableNumber, tj.idWeek
FROM doctors doc INNER JOIN timetable tt INNER JOIN specialization sp INNER JOIN day d INNER JOIN timetableday ttd INNER JOIN timetabletime ttt INNER JOIN time t INNER JOIN time ti INNER JOIN cabinets cab INNER JOIN weeks w INNER JOIN timeofjob tj
		ON 	tt.tableNumber = doc.tableNumber -- связь timetable с doctors ORDER BY d.idDay
        -- ORDER BY doc.tableNumber
		AND sp.specializationCode = doc.specializationCode -- связь specialization с doctors
        
        AND cab.numberOfCabinet = tt.numberOfCabinet -- связь timetable с cabinets
        
        AND ttd.tableNumber = tt.tableNumber -- связь timetable с timetableday
        AND ttd.idDay = d.idDay -- связь timetableday с day
        
        AND ttt.idDay = ttd.idDay
        AND ttt.tableNumber = tt.tableNumber -- связь timetable с timetabletime
        AND tj.idTimeBegin = t.idTime -- связь time с timetabletime
        AND tj.idTimeEnd = ti.idTime -- связь time с timetabletime
        AND w.idDay = d.idDay
        AND tj.tableNumber = ttt.tableNumber
        AND tj.idWeek = w.idWeek ORDER BY doc.tableNumber, w.idDay;