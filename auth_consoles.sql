DROP FUNCTION func_get_admin_by_username;
create or replace function func_get_admin_by_username(p_username varchar)
returns TABLE (
    out_username VARCHAR,
    out_password VARCHAR
              )
    LANGUAGE plpgsql
    AS $$
    begin
        RETURN QUERY
        SELECT username,password FROM admin
        WHERE p_username = admin.username;
    end;
    $$