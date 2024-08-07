-- 작성 전(사고 흐름)
날짜 연도랑 달 한번에 비교 어떻게 하지
일단 아는대로 풀어야겠다
예약 취소건은 빼고

-- 작성 1(시작) 제출 -> 실패
SELECT
    a.MCDP_CD as 진료과코드,
    count(*) as 5월예약건수
from
    appointment a
where
    a.APNT_CNCL_YN = "N"
    and year(a.APNT_YMD) = 2022
    and month(a.APNT_YMD) = 05
group by
    a.MCDP_CD
order by
    5월예약건수,
    진료과코드
왜 틀렸지
예약 취소에 null인거도 포함해야하나
그럼 취소 일자 null인거로 해야겠다

-- 작성 2(취소 여부에서 취소 일자로 변경) 제출 -> 실패
SELECT
    a.MCDP_CD as 진료과코드,
    count(*) as 5월예약건수
from
    appointment a
where
    a.APNT_CNCL_YMD is null
    and year(a.APNT_YMD) = 2022
    and month(a.APNT_YMD) = 05
group by
    a.MCDP_CD
order by
    5월예약건수,
    진료과코드
왜 틀렸지
출력 컬럼 명에 띄어쓰기 빠진게 문젠가

-- 작성 3(띄어쓰기 포함) 제출 -> 실패
SELECT
    a.MCDP_CD as "진료과 코드",
    count(*) as 5월예약건수
from
    appointment a
where
    a.APNT_CNCL_YMD is null
    and year(a.APNT_YMD) = 2022
    and month(a.APNT_YMD) = 05
group by
    a.MCDP_CD
order by
    5월예약건수,
    "진료과 코드"
질문보니까 열받게 5월 예약 목록이면 예약 취소건은 당연히 미포함해야하는데
포함해야 답이네

-- 작성 4(예약 취소건 포함) 제출 -> 성공
SELECT
    a.MCDP_CD as 진료과코드,
    count(*) as 5월예약건수
from
    appointment a
where
    year(a.APNT_YMD) = 2022
  and month(a.APNT_YMD) = 05
group by
    a.MCDP_CD
order by
    5월예약건수,
    진료과코드
괴열받

-- 작성 5(gpt 질문)
SELECT
    MCDP_CD AS "진료과코드",
    COUNT(PT_NO) AS "5월예약건수"
FROM
    APPOINTMENT
WHERE
    TO_CHAR(APNT_YMD, 'YYYY-MM') = '2022-05'
  AND (APNT_CNCL_YN IS NULL OR APNT_CNCL_YN = 'N')
GROUP BY
    MCDP_CD
ORDER BY
    COUNT(PT_NO) ASC,
    MCDP_CD ASC;
Q. 둘 중 어떤 코드가 더 효율적이야?
A.
효율성 분석
TO_CHAR 사용
장점: SQL 구문이 간결하고 직관적입니다.
단점: TO_CHAR 함수는 APNT_YMD 컬럼의 각 값을
     문자열로 변환한 후 비교하기 때문에,
     인덱스를 사용할 수 없어 성능이 저하될 수 있습니다.
     특히, 큰 테이블에서 성능 문제가 발생할 수 있습니다.
YEAR 및 MONTH 함수 사용
장점: YEAR 및 MONTH 함수는 각각의 부분을 추출하는 방식이므로
     WHERE 절에서 인덱스를 사용할 가능성이 높습니다.
     이는 성능 향상에 기여할 수 있습니다.
단점: SQL 구문이 다소 복잡해질 수 있습니다.

결론
두 번째 SQL문이 더 효율적일 가능성이 높습니다.
     이는 YEAR와 MONTH 함수가 WHERE 절에서
     인덱스를 사용할 수 있기 때문입니다.
     인덱스 사용은 대규모 데이터셋에서
     검색 성능을 크게 향상시킬 수 있습니다.