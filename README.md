# CarWorkshop_front

Link do back-endu:
https://github.com/kwisniowski/CarWorkshop

APlikacja frontendowa uruchamiana jest na porcie 8090. Większość funkcji jest widoczna od razu, 
niektóre pojawiają się dopierpo po wciśnięciu przycisków. Żeby prawidłowo przejść uzupełnienie 
wszystkich danych proponuję w takiej kolejności:

Dodajemy nowego użytkownika
Dodajemy samochód za pomocą przycisku Add car to customer
Dodajemy naprawę do samochodu przyciskiem Add repair.
Wprowadzamy do pamięci częśc zamienną.
Dodajemy koszt do naprawy - Add cost to repair - wskazujemy na liście częsci to, 
którą chcemy dodać, dodajemy ilośc zużytych części. Po potwierdzeniu koszt zostaje automatycznie naliczony.

API zewn1 - sprawdzanie statusu klienta w bazie Ministerstwa Finansów - jako okno pop-up pokazywane są 
najważniejsze informacje o kliencie, można to łatow rozbudować o edycję danych klienta w bazie na 
podstawi tych pobranych z MF. 
API zewn2 - pobieranie kursów walut z NBP.W sekcji napraw mamy do dyspozycji kalkulator - pozwala obliczyć należność, 
gdyby klient chciał zapłacić w innej walucie.

Jako potencjał do rozwoju aplikacji widzę dołożenie modułu faktuer na który brakło mi niestety czasu, 
interakcji z klientenm np zamawianie samochodu zastępczego.
