-- 작성 전
문제를 잘 못 읽어서 엄청 오래 읽음
분명 총 합인데 밑에 결과는 72만 5천이 아닌 70만이 나와있어서 오타인가보다 하고 강행
그렇게 봤을 땐 그냥 join 문제

-- 작성 1(시작) 제출 -> 실패
SELECT
    u.USER_ID,
    u.NICKNAME,
    b.PRICE as TOTAL_SALES
from
    USED_GOODS_BOARD b
        join USED_GOODS_USER u on b.WRITER_ID = u.USER_ID
where
    b.STATUS = "DONE"
  and b.PRICE >= 700000
order by
    TOTAL_SALES

문제의 총합이 맞는 거 였음
"거래가 완료 된" 을 알고도 저걸 못봐서 틀림 유유

-- 작성 2(sum, group by, having 도입) 제출 -> 성공
SELECT
    u.USER_ID,
    u.NICKNAME,
    sum(b.PRICE) as TOTAL_SALES
from
    USED_GOODS_BOARD b
        join USED_GOODS_USER u on b.WRITER_ID = u.USER_ID
where
    b.STATUS = "DONE"
group by
    u.USER_ID
having
    TOTAL_SALES >= 700000
order by
    TOTAL_SALES

바로 클리어

-- gpt 질문 시작
Q
자바는 숫자 구분이 100_000 이렇게 되잖아
mysql은 없어?
A
MySQL에서는 자바나 일부 다른 프로그래밍 언어에서 사용하는 것처럼 숫자를 구분하기 위해 밑줄(_)을 사용하는 기능은 없습니다. MySQL에서 숫자를 구분하는 방법을 지원하지 않습니다.

하지만, MySQL에서는 숫자를 읽기 쉽게 포맷팅하는 방법으로 다음과 같은 방식들을 사용할 수 있습니다:

1. FORMAT() 함수 사용
FORMAT() 함수는 숫자를 특정한 자리수와 천 단위 구분 기호로 포맷팅할 수 있습니다.
-
SELECT FORMAT(1000000, 0);
위의 쿼리를 실행하면 결과는 다음과 같이 나옵니다:
1,000,000
이 함수는 숫자를 포맷팅할 때 ,를 사용하여 천 단위로 구분하고, 두 번째 매개변수로 지정된 소수점 이하 자릿수를 설정할 수 있습니다.

2. LPAD()와 RPAD() 함수 사용
숫자를 고정된 자릿수로 맞추고 싶을 때는 LPAD() 또는 RPAD() 함수를 사용할 수 있습니다. 다만 이 방법은 숫자를 문자열로 처리할 때 주로 사용됩니다.
-
SELECT LPAD(1000000, 9, ' ');
이 쿼리는 숫자를 왼쪽에 공백을 추가하여 9자리로 맞추게 됩니다.

하지만, 일반적으로 MySQL에서는 숫자 구분을 위해 밑줄을 사용하거나 특별한 포맷을 강제하지 않고, 필요한 경우 애플리케이션 레벨에서 포맷팅을 하는 것이 일반적입니다.

Q
작성
A
SELECT U.USER_ID, U.NICKANME AS NICKNAME, SUM(B.PRICE) AS TOTAL_SALES
FROM USED_GOODS_BOARD B
    JOIN USED_GOODS_USER U ON B.WRITER_ID = U.USER_ID
WHERE B.STATUS = 'DONE'
GROUP BY U.USER_ID, U.NICKANME
HAVING SUM(B.PRICE) >= 700000
ORDER BY TOTAL_SALES ASC;

Q
비교
A
GROUP BY 절
당신의 쿼리:
u.USER_ID만 그룹화합니다.
제 쿼리:
u.USER_ID와 u.NICKANME을 그룹화합니다.
차이점:

MySQL에서는 일반적으로 GROUP BY에서 그룹화한 컬럼만 SELECT에서 사용할 수 있습니다. 하지만 MySQL의 기본 설정에서는 GROUP BY에 포함되지 않은 컬럼을 SELECT에 포함할 수 있는데, 이 경우 MySQL은 자동으로 해당 컬럼의 임의의 값을 반환합니다. 이 설정이 MySQL의 "ONLY_FULL_GROUP_BY" 모드에 의존합니다. 이 모드가 활성화된 경우, GROUP BY에 포함되지 않은 컬럼을 SELECT에서 사용할 수 없습니다.

따라서, NICKNAME이 중복되지 않는다면 GROUP BY 절에서 u.USER_ID만 그룹화해도 결과가 올바르게 반환됩니다. 하지만 이 컬럼이 중복될 가능성이 있거나 MySQL의 "ONLY_FULL_GROUP_BY" 모드가 활성화된 경우 u.NICKNAME도 GROUP BY 절에 포함시켜야 합니다.

Q
HAVING SUM(B.PRICE) >= 700000
TOTAL_SALES >= 700000
여기도 설명해줘
A
퍼포먼스 측면: 두 방식 모두 MySQL에서 동일하게 작동하며, 성능상의 차이는 없습니다. MySQL은 쿼리를 실행할 때 동일한 최적화 전략을 사용합니다.
가독성 측면: HAVING TOTAL_SALES >= 700000 방식은 코드의 가독성을 높이고, 쿼리를 더 명확하게 표현할 수 있습니다. 특히, 계산 결과를 다시 사용해야 할 때 이 방법이 유용합니다.

아싸 이겼다