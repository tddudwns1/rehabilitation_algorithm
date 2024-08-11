-- 작성 전(사고 흐름)
어렵다 뭐지
어떻게 cm 붙이지

-- 작성 1(시작) 제출 -> 성공
select
    CONCAT(max(f.length), "cm") as MAX_LENGTH
from
    FISH_INFO f

사실 한번에 한게 아니고 좀 찾아봄
concat을 몰라서 유유
그리고 cm를 붙이기 전에 max 로 데이터 출력하니까 소수점 사라져서 어떻게 할 지 고민했는데
concat 하니까 알아서 살아있었음