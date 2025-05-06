
-- Answer 1: Find maximum course codes with certain prerequisites, excluding specific cases
USE iSchool_v2;
SELECT MAX(CONCAT(course_code, course_number)) AS max_course_number,
       course_prereq
FROM courses
WHERE course_prereq NOT IN (
    SELECT CONCAT(course_code, course_number)
    FROM courses
    WHERE course_prereq LIKE "C- (INST201/301)"
)
GROUP BY course_prereq
HAVING max_course_number > "INST500"
ORDER BY max_course_number;

-- Answer 2: Get earliest course end times and unique start times per building
USE iSchool_v2;
WITH min_end_times AS (
    SELECT section_id, MIN(end_time) AS earliest_end_time
    FROM course_sections
    GROUP BY section_id
)
SELECT building_name,
       MIN(end_time) AS earliest_course_end_time,
       COUNT(DISTINCT start_time) AS unique_course_start_times
FROM locations
JOIN section_location ON locations.location_id = section_location.location_id
JOIN min_end_times ON section_location.section_id = min_end_times.section_id
JOIN course_sections ON section_location.section_id = course_sections.section_id
                     AND course_sections.end_time = min_end_times.earliest_end_time
GROUP BY building_name;
