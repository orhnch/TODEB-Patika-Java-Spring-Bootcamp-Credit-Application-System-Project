# Credit Application System

## Requirements

- _**Customer**_
    - NationalIdNumber
    - FirstName
    - LastName
    - Gender
    - Age
    - Salary
    - PhoneNumber
    - CreditScore(Random)
    - Credit(List)
- _**Credit**_
    - ApplicationDate
    - CreditLimit
    - CreditStatus
    - CreditLimitMultiplier(4)
    - Customer
<p> Customer kaydı</p>
<p> Kredi puanı hesaplama</p>
<p> Belirlenen koşullara göre müşteriye özel kredi limiti belirleme </p>
<p> Başvuru görüntüleme (Customer TC ile)</p>

## Analysis
- Customer bilgileri database'e kaydedilir, güncellenebilir, silinebilir.
- Müşteri kredi puanı oluşturulur
- Müşteriye özel belirlenen kredi limiti eklenir.
- Kredi onay bilgisi oluşturulur.
- Kredi onay bilgisi SMS ile müşteriye gönderilir.
- Müşteri kredi başvurusunu görüntüleyebilir.

## Design

![](CreditApplicationSystemUMLCase.png)