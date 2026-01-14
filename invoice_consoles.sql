-- 1. Procedure tạo Header Hóa đơn 
CREATE OR REPLACE FUNCTION func_create_invoice_header(
    p_customer_id INT,
    p_total_amount DOUBLE PRECISION
)
    RETURNS INT
    LANGUAGE plpgsql
AS $$
DECLARE
    new_id INT;
BEGIN
    INSERT INTO invoice(customer_id, total_amount, created_at)
    VALUES (p_customer_id, p_total_amount, NOW())
    RETURNING id INTO new_id;

    RETURN new_id;
END;
$$;

-- 2. Procedure thêm Chi tiết Hóa đơn & Trừ tồn kho
CREATE OR REPLACE PROCEDURE proc_add_invoice_detail(
    p_invoice_id INT,
    p_product_id INT,
    p_quantity INT,
    p_unit_price DOUBLE PRECISION
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO invoice_details(invoice_id, product_id, quantity, unit_price)
    VALUES (p_invoice_id, p_product_id, p_quantity, p_unit_price);
    UPDATE product
    SET stock = stock - p_quantity
    WHERE id = p_product_id;
END;
$$;

-- 1. Hàm Lấy danh sách hóa đơn
DROP FUNCTION IF EXISTS func_get_all_invoices;

CREATE OR REPLACE FUNCTION func_get_all_invoices()
    RETURNS TABLE (
                      out_id INT,
                      out_customer_name VARCHAR,
                      out_created_at TIMESTAMP,
                      out_total_amount DOUBLE PRECISION 
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            i.id,
            c.name,
            i.created_at,
            i.total_amount::DOUBLE PRECISION 
        FROM invoice i
                 JOIN customer c ON i.customer_id = c.id
        ORDER BY i.created_at DESC;
END;
$$;

-- 4. Function Tìm kiếm hóa đơn theo tên khách
DROP FUNCTION IF EXISTS func_search_invoice_by_customer;

CREATE OR REPLACE FUNCTION func_search_invoice_by_customer(p_keyword VARCHAR)
    RETURNS TABLE (
                      out_id INT,
                      out_customer_name VARCHAR,
                      out_created_at TIMESTAMP,
                      out_total_amount DOUBLE PRECISION
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            i.id,
            c.name,
            i.created_at,
            i.total_amount::DOUBLE PRECISION 
        FROM invoice i
                 JOIN customer c ON i.customer_id = c.id
        WHERE c.name ILIKE '%' || p_keyword || '%'
        ORDER BY i.created_at DESC;
END;
$$;

-- 5. Function Thống kê doanh thu theo Tháng
CREATE OR REPLACE FUNCTION func_revenue_by_month(p_year INT)
    RETURNS TABLE (
                      out_month DOUBLE PRECISION,
                      out_revenue DOUBLE PRECISION
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT EXTRACT(MONTH FROM created_at) as month, SUM(total_amount) as revenue
        FROM invoice
        WHERE EXTRACT(YEAR FROM created_at) = p_year
        GROUP BY month
        ORDER BY month ASC;
END;
$$;

-- 1. Fution Tìm kiếm hóa đơn theo Ngày cụ thể (yyyy-MM-dd)
DROP FUNCTION IF EXISTS func_search_invoice_by_date;

CREATE OR REPLACE FUNCTION func_search_invoice_by_date(p_date DATE)
    RETURNS TABLE (
                      out_id INT,
                      out_customer_name VARCHAR,
                      out_created_at TIMESTAMP,
                      out_total_amount DOUBLE PRECISION
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            i.id,
            c.name,
            i.created_at,
            i.total_amount::DOUBLE PRECISION 
        FROM invoice i
                 JOIN customer c ON i.customer_id = c.id
        WHERE DATE(i.created_at) = p_date
        ORDER BY i.created_at DESC;
END;
$$;
-- 2. Thống kê doanh thu theo Ngày 
DROP FUNCTION IF EXISTS func_revenue_by_day(INT, INT);

CREATE OR REPLACE FUNCTION func_revenue_by_day(p_month INT, p_year INT)
    RETURNS TABLE (
                      out_day DOUBLE PRECISION,
                      out_revenue DOUBLE PRECISION
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            EXTRACT(DAY FROM created_at)::DOUBLE PRECISION, 
            SUM(total_amount)::DOUBLE PRECISION            
        FROM invoice
        WHERE EXTRACT(MONTH FROM created_at) = p_month
          AND EXTRACT(YEAR FROM created_at) = p_year
        GROUP BY EXTRACT(DAY FROM created_at)
        ORDER BY EXTRACT(DAY FROM created_at) ASC;
END;
$$;

-- 2.  hàm Thống kê theo THÁNG
DROP FUNCTION IF EXISTS func_revenue_by_month(INT);

CREATE OR REPLACE FUNCTION func_revenue_by_month(p_year INT)
    RETURNS TABLE (
                      out_month DOUBLE PRECISION,
                      out_revenue DOUBLE PRECISION
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            EXTRACT(MONTH FROM created_at)::DOUBLE PRECISION, 
            SUM(total_amount)::DOUBLE PRECISION               
        FROM invoice
        WHERE EXTRACT(YEAR FROM created_at) = p_year
        GROUP BY EXTRACT(MONTH FROM created_at)
        ORDER BY EXTRACT(MONTH FROM created_at) ASC;
END;
$$;
-- 3. Thống kê doanh thu theo Năm 
DROP FUNCTION IF EXISTS func_revenue_by_year();

CREATE OR REPLACE FUNCTION func_revenue_by_year()
    RETURNS TABLE (
                      out_year DOUBLE PRECISION,
                      out_revenue DOUBLE PRECISION
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            EXTRACT(YEAR FROM created_at)::DOUBLE PRECISION, 
            SUM(total_amount)::DOUBLE PRECISION             
        FROM invoice
        GROUP BY EXTRACT(YEAR FROM created_at)
        ORDER BY EXTRACT(YEAR FROM created_at) ASC;
END;
$$;