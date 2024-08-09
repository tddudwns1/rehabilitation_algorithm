-- 작성 전(사고 흐름)
그냥 group by 문제네

-- 작성 1(시작) 제출 -> 성공
SELECT
    f.flavor as flavor
from
    first_half f
group by
    f.flavor
order by
    sum(f.total_order) desc,
    f.shipment_id
하면서 count가 아니고 sum 문제임을 깨달았음

-- gpt 질문
SELECT FLAVOR
FROM FIRST_HALF
ORDER BY TOTAL_ORDER DESC, SHIPMENT_ID ASC;
이거 보고 머리 띵하네
문제가 너무 허술해서 그냥 출력하는 문제였네 ㅋㅋ 아으

Q.
만약 맛별로 여러 데이터가 있고 그때 total_order가 다르게 주어진다면
ex)
초코맛 100
초코맛 200
바닐라맛 200
이런식이면 어떻게 코드 짜야해?

A.
SELECT FLAVOR, SUM(TOTAL_ORDER) AS TOTAL_SUM_ORDER
FROM FIRST_HALF
GROUP BY FLAVOR
ORDER BY TOTAL_SUM_ORDER DESC, MIN(SHIPMENT_ID) ASC;

Q.
출력하는게 맛 이름만 있으면

A.
SELECT FLAVOR
FROM (
    SELECT FLAVOR, SUM(TOTAL_ORDER) AS TOTAL_SUM_ORDER
    FROM FIRST_HALF
    GROUP BY FLAVOR
) AS SUMMED_ORDERS
ORDER BY TOTAL_SUM_ORDER DESC, MIN(SHIPMENT_ID) ASC;

Q.
내 코드랑 저 코드랑 뭐가 더 좋아?(sub쿼리 차이)

A.
결론:
첫 번째 쿼리(서브쿼리를 사용하는 쿼리)가 더 정확하고 안정적입니다.
  이 쿼리는 각 맛에 대해 총 주문량을 합산하고,
  정확하게 의도한 대로 정렬을 수행합니다.
두 번째 쿼리는 간결하긴 하지만,
  SQL 표준에 맞지 않는 부분이 있어
  데이터베이스 엔진에 따라 예기치 않은 결과를 초래할 수 있습니다.
  따라서 첫 번째 쿼리를 사용하는 것이 더 좋습니다.

이제보니 내 코드가 이상한게
group by로 묶으면서 그냥 id를 정렬로 사용한 것이다

Q.
그럼 만약 id로 정렬하지않고 총 출하량으로 정렬하면 이거랑 서브쿼리중 뭐가 더 좋아?

A.
두 번째 쿼리가 더 좋습니다.
  서브쿼리를 사용할 필요가 없는 경우,
  간단하고 효율적인 쿼리가 더 나은 선택입니다.
  특히, 이 경우에는 서브쿼리를 사용하지 않아도
  동일한 결과를 얻을 수 있으며,
  성능과 가독성 측면에서 더 이점이 있습니다.