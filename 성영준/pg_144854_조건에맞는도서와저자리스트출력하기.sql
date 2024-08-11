-- 작성 전(사고 흐름)
조인 문제네
조건에 맞게 거른 테이블을 다른 테이블에 조인하는게 맞을까
조인 후에 데이터를 조건에 맞게 거르는게 맞을까

-- 작성 1(시작) 제출 -> 성공
SELECT
    b.book_id as book_id,
    a.author_name as author_name,
    date_format(b.published_date, "%Y-%m-%d") as published_date
from
    author a
        join (
        select b.book_id as book_id , b.published_date as published_date, b.author_id
        from book b
        where b.category = '경제'
    ) b on a.author_id = b.author_id
order by
    b.published_date

조건에 먼저 걸러서 함
사실 과정중에 join 이랑 using, on 위치를 잘 못 둬서 좀 더듬거림
이제 date_format은 쉬워짐

-- gpt 질문 시작
Q.
    만약 a테이블에 b에 없는 내용이 있으면
    a right join b랑
    b right join a랑 어떤차이가 있어?
A.
    RIGHT JOIN을 사용하는 경우,
    조인의 방향에 따라 결과가 달라질 수 있습니다.
    조인의 방향에 따라 두 테이블 중 하나를 기준으로 모든 행을 반환하며,
    다른 테이블에서 일치하는 데이터를 찾습니다.
    RIGHT JOIN의 대상 테이블이 "보존"되며,
    다른 테이블의 일치하는 데이터만 조인됩니다.

Q.
    너가 코드 작성해줘
A. -
SELECT
    b.BOOK_ID as book_id,
    a.AUTHOR_NAME as author_name,
    DATE_FORMAT(b.PUBLISHED_DATE, '%Y-%m-%d') as published_date
FROM
    BOOK b
    JOIN
    AUTHOR a
ON
    b.AUTHOR_ID = a.AUTHOR_ID
WHERE
    b.CATEGORY = '경제'
ORDER BY
    b.PUBLISHED_DATE ASC;

Q.
    내 코드랑 비교해줘
A.
    주신 코드는 RIGHT JOIN을 사용함으로써
    AUTHOR 테이블에 없는 저자도 포함할 수 있도록 설계되었습니다.
    그러나 문제의 조건을 고려할 때,
    INNER JOIN을 사용하여
    두 테이블 간 일치하는 데이터만 가져오는 것이 더 적절해 보입니다.

    만약 두 테이블 간 일치하는 데이터만 필요하다면,
    제가 제공한 쿼리가 더 간결하고 목적에 맞습니다.
    RIGHT JOIN을 사용할 경우 불필요하게 NULL 값이 포함될 수 있으며,
    이는 원하지 않는 결과를 초래할 수 있습니다.

    따라서, 문제에서 요구한 대로 정확한 데이터만 포함하고 싶다면
    INNER JOIN 방식이 더 적절합니다.

Q.
    궁금한게
    b테이블에 right join을 할 때
    null이 없다는 조건하에
    미리 subquery에서 where문으로 걸러낸 테이블에 join하는게 더 좋을 수 있나?
    a랑 b를 inner join하고 where문을 하면
    join할 필요 없는 행도 join하는 과정을 거치게 되잖아
A.
    아주 좋은 질문입니다!
    이 질문은 조인의 순서와
    WHERE 절의 위치가 쿼리의 성능에 미치는 영향을 잘 이해하고 있다는 것을 보여줍니다.
    사실, 미리 WHERE 절을 사용하여 데이터를 필터링한 후에 조인을 수행하는 것이
    효율성 측면에서 매우 중요한 전략이 될 수 있습니다.

    성능 측면:
      서브쿼리에서 미리 데이터를 필터링한 후
      조인을 수행하는 것이 일반적으로 더 효율적입니다.
      이렇게 하면 조인 전에 데이터를 줄일 수 있어,
      조인 작업이 가벼워지고 성능이 향상될 가능성이 높습니다.

    데이터베이스 최적화:
      많은 데이터베이스 엔진이 내부적으로 쿼리를 최적화할 때,
      WHERE 절을 조인 전에 적용할 수 있는지 자동으로 판단합니다.
      하지만 명시적으로 서브쿼리를 사용해 미리 필터링하는 것은 쿼리의 의도를 명확히 하고,
      성능 향상에 도움이 될 수 있습니다.

    따라서, 서브쿼리에서 미리 필터링한 후에 조인을 수행하는 전략이
    성능 면에서 더 유리할 수 있습니다,
    특히 조인할 행의 수가 큰 경우에 더욱 그렇습니다.

-- 작성 2(최종) 제출 -> 성공
-
SELECT
    b.book_id as book_id,
    a.author_name as author_name,
    date_format(b.published_date, "%Y-%m-%d") as published_date
from
    author a
    join (
        select *
        from book b
        where b.category = '경제'
    ) b using (AUTHOR_ID)
order by
    b.published_date