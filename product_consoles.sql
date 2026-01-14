
-- 2. Procedure thêm sản phẩm 
CREATE OR REPLACE PROCEDURE proc_insert_product(
    p_name VARCHAR,
    p_brand VARCHAR,
    p_price DOUBLE PRECISION, 
    p_stock INT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO product(name, brand, price, stock)
    VALUES (p_name, p_brand, p_price, p_stock);
END;
$$;

-- 3. Procedure Cập nhật sản phẩm
CREATE OR REPLACE PROCEDURE proc_update_product(
    p_id INT,
    p_name VARCHAR,
    p_brand VARCHAR,
    p_price DOUBLE PRECISION,
    p_stock INT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE product
    SET name = p_name, brand = p_brand, price = p_price, stock = p_stock
    WHERE id = p_id;
END;
$$;

-- 3. Procedure Xóa sản phẩm
CREATE OR REPLACE PROCEDURE proc_delete_product(p_id INT)
    LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM product WHERE id = p_id;
END;
$$;

-- 4. Function Lấy tất cả sản phẩm 
CREATE OR REPLACE FUNCTION func_get_all_products()
    RETURNS TABLE (
                      out_id INT,
                      out_name VARCHAR,
                      out_brand VARCHAR,
                      out_price DECIMAL,
                      out_stock INT
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT id, name, brand, price, stock FROM product ORDER BY id ASC;
END;
$$;

-- 5. Function Lấy sản phẩm theo ID
CREATE OR REPLACE FUNCTION func_get_product_by_id(p_id INT)
    RETURNS TABLE (
                      out_id INT,
                      out_name VARCHAR,
                      out_brand VARCHAR,
                      out_price DECIMAL,
                      out_stock INT
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT id, name, brand, price, stock FROM product WHERE id = p_id;
END;
$$;
-- 1. Hàm tìm kiếm theo Brand
DROP FUNCTION IF EXISTS func_search_product_by_brand(VARCHAR);

CREATE OR REPLACE FUNCTION func_search_product_by_brand(p_keyword VARCHAR)
    RETURNS TABLE (
                      out_id INT,
                      out_name VARCHAR,
                      out_brand VARCHAR,
                      out_price DOUBLE PRECISION,
                      out_stock INT
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            id,
            name,
            brand,
            price::DOUBLE PRECISION,
            stock
        FROM product
        WHERE brand ILIKE '%' || p_keyword || '%'
        ORDER BY id ASC;
END;
$$;

-- 2. Hàm tìm kiếm theo Khoảng giá
DROP FUNCTION IF EXISTS func_search_product_by_price(DOUBLE PRECISION, DOUBLE PRECISION);

CREATE OR REPLACE FUNCTION func_search_product_by_price(min_price DOUBLE PRECISION, max_price DOUBLE PRECISION)
    RETURNS TABLE (
                      out_id INT,
                      out_name VARCHAR,
                      out_brand VARCHAR,
                      out_price DOUBLE PRECISION,
                      out_stock INT
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            id,
            name,
            brand,
            price::DOUBLE PRECISION,
            stock
        FROM product
        WHERE price BETWEEN min_price AND max_price
        ORDER BY price ASC;
END;
$$;

-- 3.Hàm tìm kiếm theo Tồn kho
DROP FUNCTION IF EXISTS func_search_product_by_stock(INT, INT);

CREATE OR REPLACE FUNCTION func_search_product_by_stock(min_stock INT, max_stock INT)
    RETURNS TABLE (
                      out_id INT,
                      out_name VARCHAR,
                      out_brand VARCHAR,
                      out_price DOUBLE PRECISION,
                      out_stock INT
                  )
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT
            id,
            name,
            brand,
            price::DOUBLE PRECISION,
            stock
        FROM product
        WHERE stock BETWEEN min_stock AND max_stock
        ORDER BY stock ASC;
END;
$$;