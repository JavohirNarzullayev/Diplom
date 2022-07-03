CREATE OR REPLACE VIEW plan_teacher_view AS
SELECT *
FROM public.plan_teacher
where deleted = false;



