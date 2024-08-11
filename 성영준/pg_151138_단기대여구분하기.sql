-- 작성 전(사고 흐름)
김머영씨가 어렵다고해서 봤더니 레벨은 1이고
정답률도 75%길래
하 이 형도 늙어가는구나 했는데 제가 딱 25% 나왔습니다
하하~

-- 작성 1(시작) 제출 -> 실패
SELECT
    c.HISTORY_ID as HISTORY_ID,
    c.CAR_ID as CAR_ID,
    date_format(START_DATE, "%Y-%m-%d") as START_DATE,
    date_format(END_DATE, "%Y-%m-%d") as END_DATE,
    case
        when datediff(c.END_DATE, c.START_DATE) < 30 then "단기 대여"
        else "장기 대여"
        end
        as RENT_TYPE
from
    CAR_RENTAL_COMPANY_RENTAL_HISTORY c
where
    c.START_DATE BETWEEN "2022-09-01" and "2022-09-30"
order by
    HISTORY_ID desc

사실 밑에 더 쓸 필요도 없고 그냥 문제를 덜 읽은게 문제였습니다
datediff는 말 그대로 날짜의 차이이기때문에 하루 덜 카운팅하게 됩니다

그리고 날짜가 9월인지 확인하는 방법에 대해서 gpt한테 물어봤습니다

-- gpt 질문
Q.
    위와 동일
A.
1. LIKE '2022-09%' 방법
SELECT *
FROM YOUR_TABLE
WHERE DATE_COLUMN LIKE '2022-09%';
장점: 간단하고 직관적인 방식으로 연도와 월을 일치시킬 수 있습니다.
단점: 이 방식은 문자열 비교를 수행하기 때문에 인덱스를 사용하지 못할 가능성이 있습니다. 특히, 대용량 데이터에서 성능이 저하될 수 있습니다.

2. YEAR(컬럼) = 2022 AND MONTH(컬럼) = 09 방법
SELECT *
FROM YOUR_TABLE
WHERE YEAR(DATE_COLUMN) = 2022 AND MONTH(DATE_COLUMN) = 9;
장점: 연도와 월을 개별적으로 비교하여 명확하게 조건을 설정합니다. 데이터베이스 엔진이 연도와 월에 인덱스를 사용하도록 설계된 경우, 인덱스 사용을 유도할 수 있습니다.
단점: YEAR()와 MONTH() 함수는 각각의 함수 호출을 포함하므로, 해당 함수 호출로 인해 성능이 약간 저하될 수 있습니다. 하지만 인덱스가 잘 설정된 경우, 이 방식이 더 빠를 수 있습니다.

3. DATE_FORMAT을 사용하는 방법
DATE_FORMAT 함수는 날짜를 특정 형식으로 변환할 수 있으며, 이를 통해 연도와 월이 일치하는지 확인할 수 있습니다.
SELECT *
FROM YOUR_TABLE
WHERE DATE_FORMAT(DATE_COLUMN, '%Y-%m') = '2022-09';
장점: 이 방법은 특정 형식으로 날짜를 비교할 수 있어 직관적입니다.
단점: DATE_FORMAT 함수는 인덱스를 비효율적으로 사용할 수 있으며, 대량의 데이터에서 성능이 저하될 수 있습니다.

4. BETWEEN을 사용하는 방법
BETWEEN을 사용하여 특정 연도와 월 사이의 범위를 지정할 수 있습니다.
SELECT *
FROM YOUR_TABLE
WHERE DATE_COLUMN BETWEEN '2022-09-01' AND '2022-09-30';
장점: 이 방법은 날짜 범위를 지정하는 데 유용하며, 인덱스를 효과적으로 사용할 수 있습니다.
단점: 정확한 월의 마지막 날짜를 알아야 하는 불편함이 있을 수 있습니다. 그러나 이는 대부분의 경우 문제가 되지 않습니다.

5. EXTRACT 함수를 사용하는 방법
EXTRACT 함수는 날짜에서 특정 부분(연도, 월 등)을 추출하는 데 사용됩니다.
SELECT *
FROM YOUR_TABLE
WHERE EXTRACT(YEAR FROM DATE_COLUMN) = 2022
  AND EXTRACT(MONTH FROM DATE_COLUMN) = 9;
장점: YEAR()와 MONTH() 함수와 유사하지만, 표준 SQL에서 제공하는 방법입니다. 다양한 DBMS에서 호환성이 좋습니다.
단점: 함수 호출이 추가되므로 성능이 약간 저하될 수 있습니다.

6. TRUNCATE를 사용하는 방법 (Oracle 등에서 지원)
Oracle에서는 TRUNCATE를 사용하여 날짜를 특정 단위로 자를 수 있습니다.
SELECT *
FROM YOUR_TABLE
WHERE TRUNC(DATE_COLUMN, 'MM') = TO_DATE('2022-09', 'YYYY-MM');
장점: 월 단위로 날짜를 비교하는 데 유용하며, 특정 DBMS에서 효율적일 수 있습니다.
단점: 특정 DBMS에 종속적일 수 있습니다.

결론
범용성: YEAR()와 MONTH() 함수, EXTRACT 함수는 대부분의 DBMS에서 지원되므로, 가장 일반적으로 사용될 수 있습니다.
성능: 인덱스 사용을 최대한 활용하려면 BETWEEN을 사용하는 방법이 유리할 수 있습니다.
간결성: LIKE와 DATE_FORMAT은 직관적이지만, 성능상 이슈가 있을 수 있습니다.

between이 젤 좋대요