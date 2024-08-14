-- 작성 전
어렵네 어떻게 하지
substr 사용하는 건가

-- 작성 1(시작)
SELECT
     substr(PRICE, ) as PRICE_GROUP,
     as PRODUCTS
from
    PRODUCT
where

뇌정지

-- 작성 2(gpt 질문 후) 제출 -> 성공
Q-
    mysql에서 돈 반올림 하는법
    12500원이면 12000원으로
+
    그럼 그런 데이터가 여러개일때
    같은 반올림으로 그룹 짓고싶어
A-
    여러 개의 데이터가 있을 때 동일한 반올림 값으로 그룹화하고자 한다면, FLOOR() 함수를 사용하여 반올림된 값을 계산하고 이를 기준으로 GROUP BY를 사용하여 그룹화할 수 있습니다.
-
SELECT
    floor(PRICE/10000) * 10000 as PRICE_GROUP,
    count(*) as PRODUCTS
from
    PRODUCT
group by
    PRICE_GROUP
order by
    PRICE_GROUP

Q-
truncate로도 되나?
A-
TRUNCATE() 함수는 MySQL에서 숫자를 지정된 소수점 이하 자릿수로 잘라내는(즉, 내림 처리하는) 함수입니다. 이 함수를 활용하여 가격을 만원 단위로 그룹화할 수 있습니다. FLOOR() 함수와 유사하게 작동하며, TRUNCATE() 함수를 사용해도 동일한 결과를 얻을 수 있습니다.

SELECT
    TRUNCATE(PRICE / 10000, 0) * 10000 AS PRICE_GROUP,
    COUNT(*) AS PRODUCTS
FROM
    PRODUCT
GROUP BY
    PRICE_GROUP
ORDER BY
    PRICE_GROUP;