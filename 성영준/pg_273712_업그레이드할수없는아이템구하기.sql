-- 작성 전
어렵다
sqld 칠 때 단계로 내려가는 그런거 있었는데 그건가

left join 으로 될 거 같은데
헷갈리네

일단 where 절에 subquery 로 풀어보고 검색해보자

-- 작성 1(시작)
select
    ITEM_ID,
    ITEM_NAME,
    RARITY
from
    ITEM_INFO
where
    ITEM_ID not in (
        select
            PARENT_ITEM_ID
        from
            ITEM_TREE
        group by
            PARENT_ITEM_ID
    )
order by
    ITEM_ID desc

틀린 코드 왜 틀린진 모르겠음
gpt 왈
not in 의 경우 null 이 포함되면 무조건 false 다
not exists 를 사용하면 된다

select
    ITEM_ID,
    ITEM_NAME,
    RARITY
from
    ITEM_INFO i
where
    not exists (
        select
            1
        from
            ITEM_TREE t
        WHERE
            t.PARENT_ITEM_ID = i.ITEM_ID
    )
order by
    ITEM_ID desc



not in 으론 안되는건가?

SELECT
    i.ITEM_ID,
    i.ITEM_NAME,
    i.RARITY
FROM
    ITEM_INFO i
WHERE
    i.ITEM_ID NOT IN (
        SELECT
            t.PARENT_ITEM_ID
        FROM
            ITEM_TREE t
        WHERE
            t.PARENT_ITEM_ID IS NOT NULL
    )
ORDER BY
    i.ITEM_ID DESC;


둘 비교
**NOT EXISTS**를 사용하는 방식이 더 효율적일 가능성이 높으며, 특히 대규모 데이터셋에서 더 나은 성능을 발휘할 수 있습니다.
가독성 면에서도 NOT EXISTS가 더 직관적일 수 있으며, 복잡한 조건이 많을 때 코드의 명확성을 높이는 데 유리합니다.

-
left join 으로는 안돼?

SELECT
    i.ITEM_ID,
    i.ITEM_NAME,
    i.RARITY
FROM
    ITEM_INFO i
LEFT JOIN
    ITEM_TREE t ON i.ITEM_ID = t.PARENT_ITEM_ID
WHERE
    t.PARENT_ITEM_ID IS NULL
ORDER BY
    i.ITEM_ID DESC;
되더라~
내가 헷갈렸을 뿐 되는 방식이었다 아쉽네
다시 보니 왜 헷갈렸는지가 의문이다
밤이 깊었나보다

배운점
   1. not in 은 null 을 포함하지 말자
   2. 그럴 땐 not exists 를 사용하자, 이때 내부와 외부에 alias 지정 해주자
   3. 왠만하면 join 이 신이다