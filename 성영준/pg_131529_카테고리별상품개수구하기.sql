-- 작성 전(사고 흐름)
-- 진짜 모르겠다

-- 작성 1(시작)
SELECT

from
    product p
group by

having
-- 여기서 뇌정지
-- having을 쓰면 될거라 생각했는데
-- select 절에 어떻게 보내고 뭘 하고 싶은지 갑자기 모르겠어서 뇌정지옴

-- 작성 2(검색, gpt 참고해서) 제출 -> 성공
select
    left(p.product_code, 2) as category,
    count(*) as products
from
    product p
group by
    category

-- 본격 gpt 질문 시작
-- 작성 3(substring 이용)
SELECT SUBSTRING(PRODUCT_CODE, 1, 2) AS CATEGORY,
       COUNT(*) AS PRODUCTS
FROM PRODUCT
GROUP BY CATEGORY
ORDER BY CATEGORY ASC;
-- Q. 둘 중 속도는 뭐가 더 빨라?
-- A. MySQL에서 LEFT와 SUBSTRING 함수의 성능은 일반적으로 매우 유사합니다.
-- Q. select절의 category를 group by에 사용가능한 이유가 뭐야?
-- A.
    -- 이 기능이 가능한 이유는 다음과 같습니다:
    -- SQL 처리 순서:
    -- SQL 쿼리는 논리적으로 다음 순서로 처리됩니다:
    --
    -- FROM 절에서 테이블을 선택합니다.
    -- WHERE 절에서 필터링합니다.
    -- GROUP BY 절에서 그룹화를 합니다.
    -- SELECT 절에서 컬럼을 선택합니다.
    -- ORDER BY 절에서 정렬합니다.
    -- MySQL은 이 논리적 순서에 따라 쿼리를 처리하며, SELECT 절에서 정의된 별칭을 인식하고 사용할 수 있습니다.

-- 작성 4(sub query 이용)
SELECT CATEGORY, COUNT(*) AS PRODUCTS
FROM (
         SELECT SUBSTRING(PRODUCT_CODE, 1, 2) AS CATEGORY
         FROM PRODUCT
     ) AS sub
GROUP BY CATEGORY
ORDER BY CATEGORY ASC;
-- 내가 맨 처음 생각한 풀이와 유사
-- 하지만 sub query를 이용한 풀이는 느리다는 사실을 차쌤에게서 들었음
-- Q. 속도는 더 안좋은가?
-- A. 일반적으로는 서브쿼리를 사용하는 것이 단일 쿼리보다 성능이 떨어질 가능성이 있습니다

-- 여기서 꿀팁
-- EXPLAIN 명령은 쿼리 실행 계획을 보여주며, 이를 통해 각 쿼리가 어떻게 실행되는지, 어떤 인덱스를 사용하는지, 예상되는 비용은 얼마나 되는지 등을 확인할 수 있습니다.
-- 한번 써먹어보자

-- Q. 나 궁금한게 count는 어떻게 각 분야별로 숫자를 세는거야?
-- A. COUNT 함수는 GROUP BY 절과 함께 사용하면 각 그룹별로 개수를 세는 역할을 합니다