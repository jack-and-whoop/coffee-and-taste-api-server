INSERT INTO category(CATEGORY_ID, NAME)
VALUES
(1, '음료'),
(2, '푸드'),
(3, '상품');

INSERT INTO menu_group(MENU_GROUP_ID, CATEGORY_ID, NAME, ENGLISH_NAME)
VALUES
(1, 1, '콜드 브루', 'Cold Brew'),
(2, 1, '에스프레소', 'Espresso'),
(3, 1, '프라푸치노', 'Frappuccino'),
(4, 1, '블렌디드', 'Blended'),
(5, 1, '피지오', 'Starbucks Fizzio'),
(6, 1, '티바나', 'Teavana'),
(7, 1, '브루드 커피', 'Brewed Coffee'),
(8, 1, '기타', 'Others'),
(9, 1, '병음료', 'RTD'),

(10, 2, '브레드', 'Bread'),
(11, 2, '케이크&미니디저트', 'Cake&Mini Dessert'),
(12, 2, '샌드위치&샐러드', 'Sandwich&Salad'),
(13, 2, '따뜻한 푸드', 'Hot Food'),
(14, 2, '과일&요거트', 'Fruit&Yogurt'),
(15, 2, '스낵', 'Snack'),
(16, 2, '아이스크림', 'Ice Cream'),
(17, 2, '스타디움 세트', 'Stadium Set'),

(18, 3, '머그/글라스', 'Mug/Glass'),
(19, 3, '스테인리스텀블러', 'Stainless steel Tumbler'),
(20, 3, '플라스틱텀블러', 'Plastic Tumbler'),
(21, 3, '보온병', 'Vaccum flask'),
(22, 3, '엑세서리', 'ACC'),
(23, 3, '커피용품', 'Brewing Item'),
(24, 3, '원두','Whole bean'),
(25, 3, '비아', 'VIA'),
(26, 3, '스타벅스앳홈 by 캡슐', 'Starbucks at Home by capsule'),
(27, 3, '패키지 티', 'Packaged Tea'),
(28, 3, '리저브 원두', 'Reserve Whole bean');

INSERT INTO menu(MENU_GROUP_ID, NAME, ENGLISH_NAME, PRICE, ON_SALE, DESCRIPTION, IMAGE_PATH)
VALUES
(1, '롤린 민트 초코 콜드 브루', 'Rollin Mint Choco Cold Brew', 6100, TRUE, '스타벅스의 콜드 브루와 은은한 민트 초코 베이스로\n누구나 즐길 수 있는 여름 음료.\n손목의 스냅을 춤을 추듯 가볍게 돌려 음료를 섞어서\n빨대 없이 즐겨 보세요.', '/images/rollin_mint_choco_cold_brew.jpg'),
(1, '콜드 브루 오트 라떼', 'Cold Brew With Oat Milk', 5800, TRUE, '콜드 브루의 풍미와 깔끔한 오트 밀크가 어우러진 달콤 고소한 라떼.\n식물성 밀크를 사용해 모든 고객이 부담없이 즐길 수 있는 콜드 브루 음료', '/images/cold_brew_with_oat_milk.jpg'),
(1, '돌체 콜드 브루', 'Dolce Cold Brew', 6000, TRUE, '무더운 여름철,\n동남아 휴가지에서 즐기는 커피를 떠오르게 하는\n스타벅스 음료의 베스트 x 베스트 조합인\n돌체 콜드 브루를 만나보세요!', '/images/dolce_cold_brew.jpg'),
(1, '바닐라 크림 콜드 브루', 'Vanilla Cream Cold Brew', 5800, TRUE, '콜드 브루에 더해진 바닐라 크림으로 깔끔하면서 달콤한 콜드 브루를 새롭게 즐길 수 있는 음료입니다.', '/images/vanilla_cream_cold_brew.jpg'),
(1, '콜드 브루', 'Cold Brew', 4900, TRUE, '스타벅스 바리스타의 정성으로 탄생한 콜드 브루!\n\n콜드 브루 전용 원두를 차가운 물로 14시간 동안 추출하여 한정된 양만 제공됩니다.\n깊은 풍미의 새로운 커피 경험을 즐겨보세요.', '/images/cold_brew.jpg'),
(1, '나이트로 바닐라 크림', 'Nitro Vanilla Cream', 6100, TRUE, '부드러운 목넘김의 나이트로 커피와 바닐라 크림의 매력을 한번에 느껴보세요!', '/images/nitro_vanilla_cream.jpg'),
(1, '나이트로 콜드 브루', 'Nitro Cold Brew', 6000, TRUE, '나이트로 커피 정통의 캐스케이딩과 부드러운 콜드 크레마!\n부드러운 목 넘김과 완벽한 밸런스에 커피 본연의 단맛을 경험할 수 있습니다.', '/images/nitro_cold_brew.jpg'),

(2, '바닐라 플랫 화이트', 'Vanilla Flat White', 5900, TRUE, '바닐라 플랫 화이트는 진하고 고소한 리스트레토 샷과 바닐라 시럽 그리고 스팀 밀크로 즐기는 진한 커피 라떼 입니다.', '/images/vanilla_flat_white.jpg'),
(2, '아이스 스타벅스 돌체 라떼', 'Iced Starbucks Dolce Latte', 5900, TRUE, '스타벅스의 다른 커피 음료보다 더욱 깊은 커피의 맛과 향에 깔끔한 무지방 우유와 부드러운 돌체 시럽이 들어간 음료로 달콤하고 진한 커피 라떼', '/images/iced_starbucks_dolce_latte.jpg'),
(2, '아이스 카페 모카', 'Iced Caffe Mocha', 5500, TRUE, '진한 초콜릿 모카 시럽과 풍부한 에스프레소를 신선한 우유 그리고 얼음과 섞어 휘핑크림으로 마무리한 음료로 진한 에스프레소와 초콜릿 맛이 어우러진 커피', '/images/iced_caffe_mocha.jpg'),
(2, '아이스 카페 아메리카노', 'Iced Caffe Americano', 4500, TRUE, '진한 에스프레소에 시원한 정수물과 얼음을 더하여 스타벅스의 깔끔하고 강렬한 에스프레소를 가장 부드럽고 시원하게 즐길 수 있는 커피', '/images/iced_caffe_americano.jpg'),
(2, '아이스 카페 라떼', 'Iced Caffe Latte', 5000, TRUE, '풍부하고 진한 농도의 에스프레소가 시원한 우유와 얼음을 만나 고소함과 시원함을 즐길 수 있는 대표적인 커피 라떼', '/images/iced_caffe_latte.jpg'),
(2, '아이스 카푸치노', 'Iced Cappuccino', 5000, TRUE, '풍부하고 진한 에스프레소에 신선한 우유와 우유 거품이 얼음과 함께 들어간 시원하고 부드러운 커피 음료', '/images/iced_cappuccino.jpg'),
(2, '아이스 카라멜 마키아또', 'Iced Caramel Macchiato', 5900, TRUE, '향긋한 바닐라 시럽과 시원한 우유와 얼음을 넣고 점을 찍듯이 에스프레소를 부은 후 벌집 모양으로 카라멜 드리즐을 올린 달콤한 커피 음료', '/images/iced_caramel_macchiato.jpg'),
(2, '아이스 화이트 초콜릿 모카', 'Iced White Chocolate Mocha', 5900, TRUE, '달콤하고 부드러운 화이트 초콜릿 시럽과 에스프레소를 신선한 우유 그리고 얼음과 섞어 휘핑크림으로 마무리한 음료로 달콤함과 강렬한 에스프레소가 부드럽게 어우러진 커피', '/images/iced_white_chocolate_mocha.jpg'),

(3, '더블 에스프레소 칩 프라푸치노', 'Double Espresso Chip Frappuccino', 6300, TRUE, '리스트레토 에스프레소 2샷과 에스프레소 칩, 하프앤하프가 진하게 어우러진 커피의 기본에 충실한 프라푸치노', '/images/double_espresso_chip_frappuccino.jpg'),
(3, '제주 유기농 말차로 만든 크림 프라푸치노', 'Malcha Cream Frappuccino from Jeju Organic Farm', 6300, TRUE, '깊고 진한 말차 본연의 맛과 향을 시원하고 부드럽게 즐길 수 있는 프라푸치노', '/images/malcha_cream_frappuccino_from_jeju_organic_farm.jpg'),
(3, '자바 칩 프라푸치노', 'Java Chip Frappuccino', 6300, TRUE, '커피, 모카 소스, 진한 초콜릿 칩이 입안 가득 느껴지는 스타벅스에서만 맛볼 수 있는 프라푸치노', '/images/java_chip_frappuccino.jpg'),
(3, '초콜릿 크림 칩 프라푸치노', 'Chocolate Cream Chip Frappuccino', 6000, TRUE, '모카 소스와 진한 초콜릿 칩, 초콜릿 드리즐이 올라간 달콤한 크림 프라푸치노', '/images/chocolate_cream_chip_frappuccino.jpg'),
(3, '화이트 초콜릿 모카 프라푸치노', 'White Chocolate Mocha Frappuccino', 6000, TRUE, '화이트 초콜릿, 커피와 우유가 조화로운 프라푸치노', '/images/white_chocolate_mocha_frappuccino.jpg'),
(3, '모카 프라푸치노', 'Mocha Frappuccino', 5900, TRUE, '초콜릿과 커피가 어우러진 프라푸치노', '/images/mocha_frappuccino.jpg'),
(3, '카라멜 프라푸치노', 'Caramel Frappuccino', 5900, TRUE, '카라멜과 커피가 어우러진 프라푸치노', '/images/caramel_frappuccino.jpg'),
(3, '에스프레소 프라푸치노', 'Espresso Frappuccino', 5500, TRUE, '에스프레소 샷의 강렬함과 약간의 단맛이 어우러진 프라푸치노', '/images/espresso_frappuccino.jpg'),

(4, '펀치 그래피티 블렌디드', 'Punch Graffiti Blended', 6300, TRUE, '망고,사과,핑크 구아바,파인애플 등 다양한 열대과일로 만든\n펀치 음료를 가장 힙하게 즐기는 방법.\n스트리트 문화의 대표적인 그래피티 벽화처럼 그려진 컬러 드리즐로\n힙한 감성을 느껴 보세요!', '/images/punch_graffiti_blended.jpg'),
(4, '민트 초콜릿 칩 블렌디드', 'Mint Chocolate Chip Blended', 6300, TRUE, '입 안 가득 상쾌한 민트 초콜릿과\n부드러운 돌체 소스가 어우러진 초콜릿 블렌디드', '/images/mint_chocolate_chip_blended.jpg'),
(4, '딸기 딜라이트 요거트 블렌디드', 'Strawberry Delight Yogurt Blended', 6300, TRUE, '유산균이 살아있는 리얼 요거트와 풍성한 딸기 과육이\n더욱 상큼하게 어우러진 과일 요거트 블렌디드', '/images/strawberry_delight_yogurt_blended.jpg'),
(4, '피치 & 레몬 블렌디드', 'Peach & Lemon Blended', 6300, TRUE, '복숭아와 레몬, 말랑한 복숭아 젤리가 조화로운 과일 블렌디드', '/images/peach_and_lemon_blended.jpg'),
(4, '망고 바나나 블렌디드', 'Mango Banana Blended', 6300, TRUE, '인기 음료인 망고 패션후르츠 블렌디드에 신선한 바나나 1개가 통째로 들어간 달콤한 프라푸치노', '/images/mango_banana_blended.jpg'),
(4, '망고 패션 프루트 블렌디드', 'Mango Passion Fruit Blended', 5400, TRUE, '망고 패션 프루트 주스와 블랙 티가 깔끔하게 어우러진 과일 블렌디드', '/images/mango_passion_fruit_blended.jpg'),
(4, '트위스트 피치 요거트 블렌디드', 'Twist Peach Yogurt Blended', 6800, TRUE, '에버랜드의 대표적인 스릴 어트렉션인 렛츠 트위스트에서 영감을 받은 에버랜드에서만 즐길 수 있는 에버랜드 특화 음료.상큼 달콤한 복숭아와 요거트가 트위스트된 과일 블렌디드.', '/images/twist_peach_yogurt_blended.jpg'),
(4, '레드 파워 스매시 블렌디드', 'Red Power Smash Blended', 6500, TRUE, '붉은 색의 에너지로 가득 채워진\n시원하고 상큼하게 즐기는 타트 체리 블렌디드 음료', '/images/red_power_smash_blended.jpg'),

(5, '유자 패션 피지오', 'Yuja Passion Starbucks Fizzio™', 5900, TRUE, '상콤달콤 고흥 유자와 스타벅스 시그니처 패션 탱고 티에\n한 잔 한 잔 탄산을 넣어 제조하는 피지오 음료.\n시트러스한 청량감을 가득 즐기세요!', '/images/yuja_passion_starbucks_fizzio.jpg'),
(5, '쿨 라임 피지오', 'Cool Lime Starbucks Fizzio™', 5900, TRUE, '그린 빈 추출액이 들어간 라임 베이스에 건조된 라임 슬라이스를 넣고 스파클링한 시원하고 청량감 있는 음료입니다. (카페인이 함유된 탄산음료입니다)', '/images/cool_lime_starbucks_fizzio.jpg'),
(5, '블랙 티 레모네이드 피지오', 'Black Tea Lemonade Starbucks Fizzio™', 5400, TRUE, '진하게 우린 블랙 티와 상큼한 레모네이드를 더한 청량감 있는 음료입니다.\n목 넘김이 좋은 가벼운 탄산이 첨가되어 깔끔하게 즐길 수 있습니다.', '/images/black_tea_lemonade_starbucks_fizzio.jpg'),
(5, '패션 탱고 티 레모네이드 피지오', 'Passion Tango Tea Lemonade Starbucks Fizzio™', 5400, TRUE, '꽃 향기와 달콤하고 상큼한 시트러스 향을 탄산과 함께 더욱 풍부하게 느끼고 싶으시다면 패션 티 레모네이드 피지오를 선택해 보세요! 언제 찾아도 기분이 좋아지는 훌륭한 음료입니다.', '/images/passion_tango_tea_lemonade_starbucks_fizzio.jpg'),

(6, '포멜로 플로우 그린 티', 'Pomelo Flow Green Tea', 6100, TRUE, '자몽의 한 종류인 포멜로와 라임, 제주 녹차를 함께 쉐이킹하여\n만든 가벼운 타입의 티 음료.\n리듬을 타듯 플로우를 타며 여름의 감성을 자유롭게 표현해 보세요.', '/images/pomelo_flow_green_tea.jpg'),
(6, '아이스 돌체 블랙 밀크 티', 'Iced Dolce Black Milk Tea', 5900, TRUE, '진한 홍차에 부드러운 우유와 연유 시럽으로 향긋하고\n달콤하게 맛을 낸 스타벅스만의 돌체 블랙 밀크 티', '/images/iced_dolce_black_milk_tea.jpg'),
(6, '아이스 유자 민트 티', 'Iced Yuja Mint Tea', 5900, TRUE, '달콤한 국내산 고흥 유자와 알싸하고 은은한 진저와 상쾌한 민트 티가 조화로운 유자 민트 티', '/images/iced_yuja_mint_tea.jpg'),
(6, '아이스 제주 유기농 말차로 만든 라떼', 'Iced Malcha Latte from Jeju Organic Farm', 6100, TRUE, '차광재배한 어린 녹찻잎을 곱게 갈아 깊고 진한 말차 본연의 맛과 향을\n부드럽게 즐길 수 있는 제주 유기농 말차로 만든 라떼', '/images/iced_malcha_latte_from_jeju_organic_farm.jpg'),
(6, '아이스 차이 티 라떼', 'Iced Chai Tea Latte', 5500, TRUE, '스파이시한 향과 독특한 계피향, 달콤한 차이로 만든 부드러운 티 라떼', '/images/iced_chai_tea_latte.jpg'),
(6, '아이스 자몽 허니 블랙 티', 'Iced Grapefruit Honey Black Tea', 5700, TRUE, '새콤한 자몽과 달콤한 꿀이 깊고 그윽한 풍미의 스타벅스 티바나 블랙 티의 조화', '/images/iced_grapefruit_honey_black_tea.jpg'),
(6, '아이스 제주 유기 녹차', 'Iced Jeju Organic Green Tea', 5300, TRUE, '우수한 품질의 제주도 유기농 녹차로 만든 맑은 수색과 신선한 향, 맛이 뛰어난 녹차', '/images/iced_jeju_organic_green_tea.jpg'),
(6, '아이스 잉글리쉬 브렉퍼스트 티', 'Iced English Breakfast Brewed Tea', 4500, TRUE, '인도 아삼, 제주도 유기농 홍차가 블렌딩되어 진한 벌꿀향과 그윽한 몰트향이 특징인 블랙 티', '/images/iced_english_breakfast_brewed_tea.jpg'),

(7, '오늘의 커피', 'Brewed Coffee', 4200, TRUE, '신선하게 브루드(Brewed)되어 원두의 다양함이 살아있는 커피', '/images/brewed_coffee.jpg'),
(7, '아이스 커피', 'Iced Coffee', 4500, TRUE, '깔끔하고 상큼함이 특징인 시원한 아이스 커피', '/images/iced_coffee.jpg'),

(8, '아이스 시그니처 초콜릿', 'Iced Signature Chocolate', 5700, TRUE, '깊고 진한 초콜릿과 부드러운 휘핑크림이 입안에서 사르르 녹는 프리미엄 초콜릿 음료', '/images/iced_signature_chocolate.jpg'),
(8, '스팀 우유', 'Steamed Milk', 4100, TRUE, '부드럽고 담백한 따뜻한 우유.', '/images/steamed_milk.jpg'),
(8, '제주 쑥쑥 라떼', 'Jeju Mugwort Latte', 7200, TRUE, '제주의 푸릇푸릇한 쑥 향을 느끼며\n건강과 힐링을 즐기는 음료로 제주의 오름길을 상징하는 음료.', '/images/jeju_mugwort_latte.jpg'),
(8, '아이스 제주 까망 라떼', 'Iced Jeju Black Sesame Latte', 7200, TRUE, '제주도의 돌담길과 하르방의 풍경을\n느낄 수 있는 음료로 고소한 흑임자와 달콤한 소보로 토핑으로\n시원하게 누구나 즐길 수 있는 음료', '/images/iced_jeju_black_sesame_latte.jpg'),
(8, '플러피 판다 아이스 초콜릿', 'Fluffy Panda Iced Chocolate', 6500, TRUE, '고소한 헤이즐넛과 진한 초콜릿의 만남,\n귀여운 판다까지 더해진 플러피 판다 아이스 초콜릿!', '/images/fluffy_panda_iced_chocolate.jpg'),
(8, '레드 파워 스매셔', 'Red Power Smasher', 6500, TRUE, '붉은 색의 에너지로 가득 채워진\n시원하고 상큼하게 즐기는 타트 체리 주스 음료', '/images/red_power_smasher.jpg'),
(8, '스타벅스 슬래머', 'Starbucks Slammer', 6500, TRUE, '스트로베리와 아사이베리의 상큼, 달콤한 맛이 톡톡!\n시원하고 통쾌한 그랜드 슬램을 위한 에너지 부스팅 음료~!', '/images/starbucks_slammer.jpg'),

(9, '유기농 오렌지 100% 주스 190ML', 'Organic Orange 100% Juice 190ML', 4500, TRUE, '프리미엄 유기농 오렌지 주스\n당도 높은 스페인산 오렌지 착즙 주스!\n오렌지 그대로 100% 담긴 3개 분량\n물, 설탕, 첨가물 없는 오렌지 그대로의 맛', '/images/organic_orange_100_juice_190ml.jpg'),
(9, '스타 루비 자몽 스위트 190ML', 'Star Ruby Grapefruit Sweet 190ML', 4100, TRUE, '리프레시가 필요할 땐!\n상큼 달콤한 자몽으로 채우기\n과즙이 풍부하고 당도가 높은 스타 루비 자몽이 가득 들어간 상큼한 음료', '/images/star_ruby_grapefruit_sweet_190ml.jpg'),
(9, '핑크 용과 레모네이드 190ML', 'Pink Dragon Fruit Lemonade 190ML', 4300, TRUE, '레드 용과의 상큼함에\n구아바와 망고스틴의 달콤함이 추가되어\n맛도, 컬러도 화사함이 느껴지는 음료', '/images/pink_dragon_fruit_lemonade_190ml.jpg'),
(9, '딸기 가득 요거트 190ML', 'Strawberry Yogurt 190ML', 4100, TRUE, '상큼 달콤 딸기 과육이 한가득 씹히는 마시는 요거트 음료', '/images/strawberry_yogurt_190ml.jpg'),
(9, '파이팅 청귤 190ML', 'Green Tangerine & Yuja', 4000, TRUE, '비타민C로 상큼해지는 청귤 유자 주스\n(비타민C 일일 권장 섭취량 함유)', '/images/green_tangerine_and_yuja.jpg'),
(9, '기운내라임 190ML', 'Lime & Lemon', 4000, TRUE, '비타민C를 채워주는 라임 레몬 주스\n(비타민C 일일 권장 섭취량 함유)', '/images/lime_and_lemon.jpg'),
(9, '한방에 쭉 감당 190ML', 'Tangerine & Carrot', 4000, TRUE, '자연의 맛 그대로 느낄 수 있는 감귤 당근 주스\n(비타민B 함유)', '/images/tangerine_and_carrot.jpg')
;
