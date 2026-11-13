# PipiTalk: KapsamlÄ± MÃ¼fredat ve Ä°lerleme Mimarisi

Bu belge; Duolingo ve Kids Lingo modellerini aÅŸacak ÅŸekilde hazÄ±rlanan **PipiTalk** mobil uygulamasÄ±nÄ±n tam pedagojik ve teknik mÃ¼fredat mimarisidir.

---

## 1. Genel Seviye Piramidi (CEFR Uyumlu)

Uygulama 5 ana dÃ¼nyadan (DÃ¼nya / BÃ¶lge) ve her dÃ¼nyada 10 Ã¼niteden oluÅŸur. Toplam 50 Ã¼nite, 250 ders ve 1.500+ mikro-etkinlik iÃ§erir.

```mermaid
flowchart TD
    W1["DÃ¼nya 1: TanÄ±ÅŸma ve Temeller (A0 - KeÅŸif)"] --> W2["DÃ¼nya 2: GÃ¼nlÃ¼k YaÅŸam ve Ã‡evre (A1 - BaÅŸlangÄ±Ã§)"]
    W2 --> W3["DÃ¼nya 3: Okul, Macera ve DoÄŸa (A1+ - GeliÅŸim)"]
    W3 --> W4["DÃ¼nya 4: Sosyal Diyaloglar ve Ä°fade (A2 - AkÄ±cÄ±lÄ±k)"]
    W4 --> W5["DÃ¼nya 5: Gelecek, Hayaller ve Ã–zgÃ¼r KonuÅŸma (A2+ - UstalÄ±k)"]
```

---

## 2. DÃ¼nyalar ve Ãœnite DetaylarÄ±

### DÃ¼nya 1: KeÅŸif Vadisi (A0)
* **Ãœnite 1:** Merhaba DÃ¼nya (Greetings, Self Introduction)
* **Ãœnite 2:** Renkli BahÃ§e (Colors & Basic Shapes)
* **Ãœnite 3:** SayÄ±larÄ±n DansÄ± (Numbers 1-20 & Counting)
* **Ãœnite 4:** Sevimli Dostlar (Animals & Sounds)
* **Ãœnite 5:** Ailem ve Evim (Family Members & Home)
* **Ãœnite 6:** Meyve Sepeti (Fruits & Tastes)
* **Ãœnite 7:** KÄ±yafet DolabÄ± (Clothes & Weather)
* **Ãœnite 8:** Bedenim ve Hareket (Body Parts & Action Verbs)
* **Ãœnite 9:** Oyuncak Kutusu (Toys & Prepositions: in, on, under)
* **Ãœnite 10:** Ara DeÄŸerlendirme & Ä°lk Rozet (Boss Challenge: Pipi's Tea Party)

### DÃ¼nya 2: GÃ¼nlÃ¼k Kasaba (A1)
* **Ãœnite 11:** KahvaltÄ± ZamanÄ± (Food & Drinks, 'I like / I don't like')
* **Ãœnite 12:** Saat KaÃ§? (Telling Time, Daily Routine)
* **Ãœnite 13:** Åehirde Gezinti (Places in Town, Directions)
* **Ãœnite 14:** DuygularÄ±mÄ±z (Feelings: Happy, Sad, Excited, Tired)
* **Ãœnite 15:** Meslekler (Jobs & Occupations)
* **Ãœnite 16:** GÃ¼nler, Aylar ve Mevsimler (Calendar & Weather Routine)
* **Ãœnite 17:** SÃ¼permarkette (Shopping, How much / How many)
* **Ãœnite 18:** UlaÅŸÄ±m AraÃ§larÄ± (Vehicles & Travel)
* **Ãœnite 19:** Ev Ä°Ã§i GÃ¶revler (House Chores, 'Can / Can't')
* **Ãœnite 20:** Kasaba Festivali (Boss Challenge: Market Master)

### DÃ¼nya 3: DoÄŸa ve Macera (A1+)
* **Ãœnite 21:** Orman Gezisi (Wild Animals & Habitats)
* **Ãœnite 22:** Deniz AltÄ± DÃ¼nyasÄ± (Sea Creatures & Adjectives: big, fast)
* **Ãœnite 23:** SaÄŸlÄ±klÄ± YaÅŸam (Sports, Healthy Habits)
* **Ãœnite 24:** Hobiler ve Yetenekler (Musical Instruments, Crafting)
* **Ãœnite 25:** DoÄŸum GÃ¼nÃ¼ Partisi (Invitations, Months, Giving Gifts)
* **Ãœnite 26:** Kamp AteÅŸi (Camping, Simple Past: What happened?)
* **Ãœnite 27:** Uzay ve Gezegenler (Planets, Stars, Comparisons)
* **Ãœnite 28:** Ã‡iftlik GÃ¼nlÃ¼ÄŸÃ¼ (Farm Life, Present Continuous)
* **Ãœnite 29:** Bilim KulÃ¼bÃ¼ (Simple Experiments & Questions: Why? How?)
* **Ãœnite 30:** DoÄŸa KaÅŸifi (Boss Challenge: Jungle Survival)

### DÃ¼nya 4: Ä°letiÅŸim AdasÄ± (A2)
* **Ãœnite 31:** Restoranda SipariÅŸ (Ordering Food, Politeness)
* **Ãœnite 32:** Doktor Randevusu (Health Problems, Giving Advice: Should)
* **Ãœnite 33:** KayÄ±p EÅŸya BÃ¼rosu (Describing Objects & People)
* **Ãœnite 34:** Tatil PlanlarÄ± (Future Plans: Going to, Travel destinations)
* **Ãœnite 35:** Sinema ve Masallar (Storytelling, Sequencing words)
* **Ãœnite 36:** Ã‡evre Dostu Pipi (Recycling, Planet Protection)
* **Ãœnite 37:** Telefon GÃ¶rÃ¼ÅŸmesi (Making Calls, Leaving Messages)
* **Ãœnite 38:** AlÄ±ÅŸveriÅŸ Merkezi (Fashion, Sizes, Preferences)
* **Ãœnite 39:** Ä°lginÃ§ Meslekler (Inventors, Astronauts, Past Tense)
* **Ãœnite 40:** Ada RÃ¶portajÄ± (Boss Challenge: Interview Pipi)

### DÃ¼nya 5: Bilge Zirve (A2+)
* **Ãœnite 41:** DÃ¼nya KÃ¼ltÃ¼rleri (Countries, Nationalities, Traditions)
* **Ãœnite 42:** GeleceÄŸin Åehirleri (Technology, AI, Inventions)
* **Ãœnite 43:** Problem Ã‡Ã¶zme (Giving Opinions: I think, Because...)
* **Ãœnite 44:** Gizemli Macera (Detective Stories, Deduction)
* **Ãœnite 45:** Sanat ve YaratÄ±cÄ±lÄ±k (Paintings, Photography, Music Genres)
* **Ãœnite 46:** Dostluk ve Empati (Sharing emotions, Resolving conflicts)
* **Ãœnite 47:** Bilim Kurgu DÃ¼nyasÄ± (Future Tense: Will, Imagining)
* **Ãœnite 48:** MÃ¼nazara Saati (Debating, Pros and Cons)
* **Ãœnite 49:** HayatÄ±mÄ±n KitabÄ± (Reflecting on Achievements)
* **Ãœnite 50:** BÃ¼yÃ¼k Mezuniyet (Final Challenge: Free Talk with Pipi)

---

## 3. GÃ¼nlÃ¼k Ders AkÄ±ÅŸÄ± ve Mikro-AlÄ±ÅŸtÄ±rma Tipleri

Her Ã¼nite 5 dersten, her ders ise 6-8 interaktif adÄ±mdan oluÅŸur:

| SÄ±ra | AÅŸama | AmaÃ§ | Etkinlik Tipi |
| :--- | :--- | :--- | :--- |
| 1 | **KeÅŸif (Discover)** | Kelime ve kalÄ±bÄ± ilk kez duyma | Resim eÅŸleme, sesli dinleme, maskot tepkisi |
| 2 | **PekiÅŸtirme (Reinforce)** | HafÄ±zaya alma | Ã‡ift kart eÅŸleÅŸtirme, eksik harf tamamlama |
| 3 | **CÃ¼mle Kurma (Build)** | Gramer mantÄ±ÄŸÄ±nÄ± sezgisel Ã¶ÄŸrenme | Kelime bloklarÄ±nÄ± sÄ±raya dizme |
| 4 | **Dinleme & Anlama (Listen)** | Kulak dolgunluÄŸu | Ses kaydÄ±nÄ± dinleyip doÄŸru gÃ¶rseli/cevabÄ± seÃ§me |
| 5 | **KonuÅŸma & Telaffuz (Speak)** | AÄŸÄ±z alÄ±ÅŸkanlÄ±ÄŸÄ± | Mikrofona sÃ¶yleme (Pipi ses tanÄ±ma geri bildirimi) |
| 6 | **Pipi ile CanlÄ± Diyalog (AI Chat)** | Serbest pratik | Pipi ile 2 turlu mini sesli konuÅŸma |

---

## 4. PipiTalk Ã–zel Dinamikleri (Duolingo'yu AÅŸan Noktalar)

1. **Serbest AI KonuÅŸma Modu (Pipi Live):**
   * Sadece kalÄ±plarÄ± ezberletmez; Ã§ocuk "BugÃ¼n ne yaptÄ±n?" sorusuna kendi cÃ¼mleleriyle cevap verebilir.
2. **Ã‡ocuk Dostu Hata ToleransÄ±:**
   * Duolingo gibi katÄ± can cezalandÄ±rmasÄ± yerine, ÅŸefkatli geri bildirim ve ipucu sistemi.
3. **Ã–zgÃ¼n Maskot Ruh Hali (Pipi Emotions):**
   * Pipi Ã§ocuk konuÅŸtukÃ§a alkÄ±ÅŸlar, dans eder, ÅŸaÅŸÄ±rÄ±r veya ipucu verir.
4. **Ã‡evrimdÄ±ÅŸÄ± Ã‡alÄ±ÅŸma Garantisi:**
   * TÃ¼m temel dersler internet olmadan da Ã§alÄ±ÅŸÄ±r. Sadece ileri seviye AI konuÅŸma internetle zenginleÅŸir.
