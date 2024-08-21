-- 작성 전
sql 에서도 비트 연산이 되나?

-- 작성 1
select
    ID,
    EMAIL,
    FIRST_NAME,
    LAST_NAME
from
    DEVELOPERS
where
    SKILL_CODE & (
        select
            sum(CODE)
        from
            SKILLCODES
        where
            NAME in ('Python', 'C#')
    ) > 0
order by
    ID
where 절에 subquery 써서 별론 듯

-- 작성 2
SELECT
    DISTINCT(ID),
    EMAIL,
    FIRST_NAME,
    LAST_NAME
FROM
    DEVELOPERS
    JOIN
    SKILLCODES
        ON DEVELOPERS.SKILL_CODE & SKILLCODES.CODE
WHERE
    NAME="C#"
    OR NAME="Python"
ORDER BY
    ID
이게 훨씬 좋네