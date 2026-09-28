#!/bin/bash
# Popula o banco com temporadas (1995-2026) e 51 animes reais.
# Seguro rodar mais de uma vez: entradas já existentes só são ignoradas (409/404).
BASE="http://localhost:8080"

echo "== Cadastrando temporadas =="
SEASONALS=("SPRING" "SUMMER" "FALL" "WINTER")

seasons_ok=0
seasons_skip=0
for year in 1995 1997 1998 $(seq 1999 2026); do
  for seasonal in "${SEASONALS[@]}"; do
    status=$(curl -s -o /dev/null -w "%{http_code}" -X POST "$BASE/seasons" \
      -H "Content-Type: application/json" \
      -d "{\"year\":$year,\"seasonal\":\"$seasonal\"}")
    if [ "$status" = "201" ]; then
      seasons_ok=$((seasons_ok+1))
    else
      seasons_skip=$((seasons_skip+1))
    fi
  done
done
echo "Seasons: $seasons_ok criadas, $seasons_skip já existiam/erro"

echo "== Cadastrando animes =="

post_anime() {
  local title="$1" day="$2" episodes="$3" studio="$4" image="$5" year="$6" seasonal="$7"
  status=$(curl -s -o /tmp/anime_resp.json -w "%{http_code}" -X POST "$BASE/animes" \
    -H "Content-Type: application/json" \
    -d "{\"title\":\"$title\",\"dayOfWeek\":\"$day\",\"totalEpisodes\":$episodes,\"studio\":\"$studio\",\"imageUrl\":\"$image\",\"seasonYear\":$year,\"seasonal\":\"$seasonal\"}")
  if [ "$status" = "201" ]; then
    animes_ok=$((animes_ok+1))
  else
    animes_fail=$((animes_fail+1))
    echo "FALHOU: $title ($status) -> $(cat /tmp/anime_resp.json)"
  fi
}

animes_ok=0
animes_fail=0

post_anime "Cowboy Bebop" "SATURDAY" 26 "Sunrise" "https://cdn.myanimelist.net/images/anime/4/19644.jpg" 1998 "SPRING"
post_anime "Naruto" "THURSDAY" 220 "Studio Pierrot" "https://cdn.myanimelist.net/images/anime/13/17405.jpg" 2002 "FALL"
post_anime "One Piece" "SUNDAY" 1000 "Toei Animation" "https://cdn.myanimelist.net/images/anime/6/73245.jpg" 1999 "FALL"
post_anime "Bleach" "WEDNESDAY" 366 "Studio Pierrot" "https://cdn.myanimelist.net/images/anime/3/40451.jpg" 2004 "FALL"
post_anime "Death Note" "WEDNESDAY" 37 "Madhouse" "https://cdn.myanimelist.net/images/anime/9/9453.jpg" 2006 "FALL"
post_anime "Fullmetal Alchemist: Brotherhood" "SUNDAY" 64 "Bones" "https://cdn.myanimelist.net/images/anime/1208/94745.jpg" 2009 "SPRING"
post_anime "Attack on Titan" "SATURDAY" 25 "Wit Studio" "https://cdn.myanimelist.net/images/anime/10/47347.jpg" 2013 "SPRING"
post_anime "Demon Slayer: Kimetsu no Yaiba" "SATURDAY" 26 "ufotable" "https://cdn.myanimelist.net/images/anime/1286/99889.jpg" 2019 "SPRING"
post_anime "Jujutsu Kaisen" "THURSDAY" 24 "MAPPA" "https://cdn.myanimelist.net/images/anime/1171/109222.jpg" 2020 "FALL"
post_anime "My Hero Academia" "SATURDAY" 13 "Bones" "https://cdn.myanimelist.net/images/anime/10/78745.jpg" 2016 "SPRING"
post_anime "Hunter x Hunter" "SUNDAY" 148 "Madhouse" "https://cdn.myanimelist.net/images/anime/1337/99013.jpg" 2011 "FALL"
post_anime "Steins;Gate" "THURSDAY" 24 "White Fox" "https://cdn.myanimelist.net/images/anime/5/73199.jpg" 2011 "SPRING"
post_anime "Code Geass: Hangyaku no Lelouch" "SUNDAY" 25 "Sunrise" "https://cdn.myanimelist.net/images/anime/5/50331.jpg" 2006 "FALL"
post_anime "Sword Art Online" "SUNDAY" 25 "A-1 Pictures" "https://cdn.myanimelist.net/images/anime/11/39717.jpg" 2012 "SUMMER"
post_anime "Tokyo Ghoul" "TUESDAY" 12 "Studio Pierrot" "https://cdn.myanimelist.net/images/anime/1498/134443.jpg" 2014 "SUMMER"
post_anime "One Punch Man" "SATURDAY" 12 "Madhouse" "https://cdn.myanimelist.net/images/anime/12/76049.jpg" 2015 "FALL"
post_anime "Mob Psycho 100" "SATURDAY" 12 "Bones" "https://cdn.myanimelist.net/images/anime/8/80356.jpg" 2016 "SUMMER"
post_anime "Chainsaw Man" "TUESDAY" 12 "MAPPA" "https://cdn.myanimelist.net/images/anime/1806/126216.jpg" 2022 "FALL"
post_anime "Vinland Saga" "SUNDAY" 24 "Wit Studio" "https://cdn.myanimelist.net/images/anime/1500/103005.jpg" 2019 "SUMMER"
post_anime "Frieren: Beyond Journey's End" "FRIDAY" 28 "Madhouse" "https://cdn.myanimelist.net/images/anime/1015/138006.jpg" 2023 "FALL"
post_anime "Spy x Family" "SATURDAY" 12 "Wit Studio" "https://cdn.myanimelist.net/images/anime/1441/122795.jpg" 2022 "SPRING"
post_anime "Haikyuu!!" "SUNDAY" 25 "Production I.G" "https://cdn.myanimelist.net/images/anime/7/76014.jpg" 2014 "SPRING"
post_anime "Kaguya-sama: Love is War" "SATURDAY" 12 "A-1 Pictures" "https://cdn.myanimelist.net/images/anime/1295/106551.jpg" 2019 "WINTER"
post_anime "Re:Zero - Starting Life in Another World" "TUESDAY" 25 "White Fox" "https://cdn.myanimelist.net/images/anime/1522/128039.jpg" 2016 "SPRING"
post_anime "KonoSuba" "THURSDAY" 10 "Studio Deen" "https://cdn.myanimelist.net/images/anime/8/77831.jpg" 2016 "WINTER"
post_anime "No Game No Life" "THURSDAY" 12 "Madhouse" "https://cdn.myanimelist.net/images/anime/5/65187.jpg" 2014 "SPRING"
post_anime "Dr. Stone" "SUNDAY" 24 "TMS Entertainment" "https://cdn.myanimelist.net/images/anime/1613/102576.jpg" 2019 "SUMMER"
post_anime "Black Clover" "TUESDAY" 170 "Studio Pierrot" "https://cdn.myanimelist.net/images/anime/2/88336.jpg" 2017 "FALL"
post_anime "Erased" "THURSDAY" 12 "A-1 Pictures" "https://cdn.myanimelist.net/images/anime/10/77957.jpg" 2016 "WINTER"
post_anime "Your Lie in April" "THURSDAY" 22 "A-1 Pictures" "https://cdn.myanimelist.net/images/anime/3/67177.jpg" 2014 "FALL"
post_anime "Neon Genesis Evangelion" "WEDNESDAY" 26 "Gainax" "https://cdn.myanimelist.net/images/anime/1314/108941.jpg" 1995 "FALL"
post_anime "Monster" "FRIDAY" 74 "Madhouse" "https://cdn.myanimelist.net/images/anime/3/24317.jpg" 2004 "SPRING"
post_anime "Parasyte: The Maxim" "FRIDAY" 24 "Madhouse" "https://cdn.myanimelist.net/images/anime/3/73178.jpg" 2014 "FALL"
post_anime "Psycho-Pass" "FRIDAY" 22 "Production I.G" "https://cdn.myanimelist.net/images/anime/5/43399.jpg" 2012 "FALL"
post_anime "Made in Abyss" "FRIDAY" 13 "Kinema Citrus" "https://cdn.myanimelist.net/images/anime/6/86733.jpg" 2017 "SUMMER"
post_anime "The Promised Neverland" "TUESDAY" 12 "CloverWorks" "https://cdn.myanimelist.net/images/anime/1125/96929.jpg" 2019 "WINTER"
post_anime "JoJo's Bizarre Adventure" "SATURDAY" 26 "David Production" "https://cdn.myanimelist.net/images/anime/3/40409.jpg" 2012 "FALL"
post_anime "Gintama" "THURSDAY" 201 "Sunrise" "https://cdn.myanimelist.net/images/anime/10/73274.jpg" 2006 "SPRING"
post_anime "Berserk" "SUNDAY" 25 "OLM" "https://cdn.myanimelist.net/images/anime/1384/119988.jpg" 1997 "FALL"
post_anime "Samurai Champloo" "THURSDAY" 26 "Manglobe" "https://cdn.myanimelist.net/images/anime/11/29150.jpg" 2004 "SPRING"
post_anime "Trigun" "THURSDAY" 26 "Madhouse" "https://cdn.myanimelist.net/images/anime/7/20310.jpg" 1998 "SPRING"
post_anime "Black Lagoon" "THURSDAY" 12 "Madhouse" "https://cdn.myanimelist.net/images/anime/4/25596.jpg" 2006 "SPRING"
post_anime "Fate/Zero" "SATURDAY" 13 "ufotable" "https://cdn.myanimelist.net/images/anime/1887/117644.jpg" 2011 "FALL"
post_anime "Fate/stay night: Unlimited Blade Works" "SATURDAY" 12 "ufotable" "https://cdn.myanimelist.net/images/anime/12/67333.jpg" 2014 "FALL"
post_anime "Overlord" "SUNDAY" 13 "Madhouse" "https://cdn.myanimelist.net/images/anime/7/88019.jpg" 2015 "SUMMER"
post_anime "That Time I Got Reincarnated as a Slime" "TUESDAY" 24 "Eight Bit" "https://cdn.myanimelist.net/images/anime/1694/93337.jpg" 2018 "FALL"
post_anime "Mushoku Tensei: Jobless Reincarnation" "SATURDAY" 11 "Studio Bind" "https://cdn.myanimelist.net/images/anime/1530/117776.jpg" 2021 "WINTER"
post_anime "Solo Leveling" "SATURDAY" 12 "A-1 Pictures" "https://cdn.myanimelist.net/images/anime/1807/140916.jpg" 2024 "WINTER"
post_anime "Oshi no Ko" "WEDNESDAY" 11 "Doga Kobo" "https://cdn.myanimelist.net/images/anime/1812/134831.jpg" 2023 "SPRING"
post_anime "Bocchi the Rock!" "THURSDAY" 12 "CloverWorks" "https://cdn.myanimelist.net/images/anime/1448/127956.jpg" 2022 "FALL"
post_anime "Cyberpunk: Edgerunners" "WEDNESDAY" 10 "Studio Trigger" "https://cdn.myanimelist.net/images/anime/1818/126435.jpg" 2022 "FALL"

echo "Animes: $animes_ok criados, $animes_fail falharam"
rm -f /tmp/anime_resp.json
