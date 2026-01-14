-- 1. Procedure Thêm khách hàng
CREATE OR REPLACE PROCEDURE proc_insert_customer(
    p_name VARCHAR,
    p_phone VARCHAR,
    p_email VARCHAR,
    p_address VARCHAR
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO customer(name, phone, email, address)
    VALUES (p_name, p_phone, p_email, p_address);
END;
$$;

-- 2. Procedure Cập nhật khách hàng
CREATE OR REPLACE PROCEDURE proc_update_customer(
    p_id INT,
    p_name VARCHAR,
    p_phone VARCHAR,
    p_email VARCHAR,
    p_address VARCHAR
)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE customer
    SET name = p_name, phone = p_phone, email = p_email, address = p_address
    WHERE id = p_id;
END;
$$;

-- 3. Procedure Xóa khách hàng
CREATE OR REPLACE PROCEDURE proc_delete_customer(p_id INT)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM customer WHERE id = p_id;
END;
$$;

-- 4. Function Lấy tất cả khách hàng
CREATE OR REPLACE FUNCTION func_get_all_customers()
    RETURNS TABLE (
                      out_id INT,
                      out_name VARCHAR,
                      out_phone VARCHAR,
                      out_email VARCHAR,
                      out_address VARCHAR
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT id, name, phone, email, address FROM customer ORDER BY id ASC;
END;
$$;

-- 5. Function Lấy khách hàng theo ID
CREATE OR REPLACE FUNCTION func_get_customer_by_id(p_id INT)
    RETURNS TABLE (
                      out_id INT,
                      out_name VARCHAR,
                      out_phone VARCHAR,
                      out_email VARCHAR,
                      out_address VARCHAR
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT id, name, phone, email, address FROM customer WHERE id = p_id;
END;
$$;