-- 작성 전
쉬운데 average 함수는 없겠지

-- 작성 1(시작) 제출 -> 성공
SELECT
    floor(sum(DAILY_FEE) / count(*)) as AVERAGE_FEE
from
    CAR_RENTAL_COMPANY_CAR
where
    CAR_TYPE = "SUV"
group by
    CAR_TYPE

-- GPT 질문
SELECT ROUND(AVG(DAILY_FEE), 0) AS AVERAGE_FEE
FROM CAR_RENTAL_COMPANY_CAR
WHERE CAR_TYPE = 'SUV';

비교
첫 번째 쿼리:
AVG(DAILY_FEE)를 사용하여 DAILY_FEE의 평균을 구합니다. 이 함수는 내부적으로 SUM(DAILY_FEE) / COUNT(*)를 수행합니다.
ROUND(AVG(DAILY_FEE), 0)는 평균을 소수 첫 번째 자리에서 반올림합니다.

두 번째 쿼리:
SUM(DAILY_FEE) / COUNT(*)를 사용하여 직접 평균을 계산합니다. COUNT(*)는 SUV인 자동차의 개수를 계산합니다.
FLOOR 함수를 사용하여 소수점을 버리고, 소수점 아래를 내림 처리합니다.

나는 floor 사용한 게 버림 이었고,
round를 사용해 반올림을 해야했기에 이 문제의 테스트케이스가 부족해서 통과했다

그리고 sum 과 count 를 개별적으로 진행했는데
avg 를 사용하면 됐다
average로 입력해서 안 되던 것이었다