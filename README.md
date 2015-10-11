## Przetwarzanie Języka Naturalnego

### Zadania
#### Zadanie 1 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab1.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab1.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab1.tar.gz))
- [x] Napisać program budujący statystykę n-gramów dla różnych języków (1 pkt)
- [x] Napisać program odgadujący język zdania wprowadzonego przez użytkownika (1 pkt)
- [x] Przeanalizować wyniki odgadywania w zależności od n (1 pkt)

#### Zadanie 2 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab2.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab2.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab2.tar.gz))
- [x] Napisać program wyliczający odległość Levenshteina między dwoma wprowadzonymi słowami (0.5 pkt)
- [x] Dokonać modyfikacji funkcji wyliczającej odległość Levenshteina, aby uwzględniała ona:
  - [x] błędy ortograficzne w języku polskim (0.5 pkt)
  - [x] znaki diakrytyczne w jęezyku polskim (0.5 pkt)
  - [ ] tzw. "czeskie błędy" (0.5 pkt)
- [x] Korzystając z listy form występujących w języku polskim, dokonać korekty wprowadzonych wyrazów (1 pkt)

#### Zadanie 3 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab3.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab3.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab3.tar.gz))
- [x] Napisać funkcję obliczającą prawdopodobieństwo błędu P(w|c) (1 pkt)
- [x] Zebrać statystyki występowania form w korpusie (1 pkt)
- [ ] Korzystając z naiwnego klasyfikatora Bayesa zaproponować najlepszą poprawkę dla wpisanego słowa (1 pkt)

#### Zadanie 4 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab4.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab4.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab4.tar.gz))
- [ ] Napisać program klasteryzujący nazwy firm z pliku `lines.txt`:
  - [ ] Wykonać potrzebny preprocessing (stworzyć stoplistę, etc.) (1 pkt)
  - [ ] Dokonać klasteryzacji przy pomocy wybranej metryki (1 pkt)
  - [ ] Przy pomocy miar precision, recall i F1 porównać otrzymany wynik z klastrami z pliku `clusters.txt` (1 pkt)

#### Zadanie 5 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab5.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab5.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab5.tar.gz))
- [ ] Napisać funkcję budującą łańcuch Markova z podanych n-gramów (1 pkt)
- [ ] Napisać funkcję generującą losowe notatki prasowe na podstawie korpusu PAP (1 pkt)
- [ ] Napisać funkcję generującą losowe słowa w języku polskim (1 pkt)

#### Zadanie 6 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab6.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab6.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab6.tar.gz))
- [x] Wykorzystując plik `odm.txt` (lista słów z odmianami z SJP), sprowadzić wszystkie wyrazy z pliku `potop.txt` do formy podstawowej, a następnie stworzyć posortowaną listę rankingową częstosści wystąpień poszczególnych wyrazów (1.5 pkt)
- [ ] Dla powstałej listy narysowacć wykres ilustrujący Prawa Zipfa i Mandelbrota (1 pkt)
- [x] Zliczyć hapax legomena i ilość wyrazów, które obejmują 50% tekstu (0.5 pkt)

#### Zadanie 7 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab7.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab7.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab7.tar.gz))
- [x] Zbudować macierz tf-idf dla korpusu PAP (0.5 pkt)
- [x] Dla każdej notatki wygenerować słowa kluczowe (0.5 pkt)
- [x] Napisać program wyszukujący notatki na podstawie słów (1 pkt)
- [x] Napisać program wyszukujący notatki podobne do wybranej (1 pkt)

#### Zadanie 8 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab8.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab8.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab8.tar.gz))
- [ ] Zbudować modele LSA i LDA przy użyciu np. [biblioteki gensim](http://radimrehurek.com/gensim/tutorial.html). Proszę pamiętać o sprowadzeniu wyrazów do formy podstawowej (1.5 pkt)
- [ ] Napisać program, który dla danej notatki z korpusu PAP przedstawi N najbardziej zbliżonych tematycznie notatek i wypisze M najistotniejszych tematów tych notatek w modelach LSA i LDA (1.5 pkt)

#### Zadanie 9 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab9.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab9.pdf), [Pliki](http://home.agh.edu.pl/~wojtek/pjn2015/lab9.tar.gz))
- [ ] Stworzyć stoplistę (0.5 pkt)
- [ ] Przekonwertować korpus PAP na model grafowy (1 pkt)
- [ ] Dla wybranych 10 notatek znaleźć po 10 najbliższych notatek dla k od 0 do 4 (1.5 pkt)

#### Zadanie 10 ([PDF](http://home.agh.edu.pl/~wojtek/pjn2015/lab10.pdf), [Kopia](https://github.com/mkierc/pjn/blob/master/pdf/lab10.pdf))
- [ ] Napisać program ustalający rekcję 5 wybranych przyimków (1.5 – 3 pkt)
