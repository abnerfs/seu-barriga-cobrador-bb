# Seu Barriga Cobrador
![](https://media.tenor.com/c2DMXiQDJRkAAAAC/pay-rent.gif)

I share a Youtube Premium subscription with some family members that never remember to pay, so I created this script to send them a friendly e-mail that they owe me money using smtp.

# Requirements
- [Babashka](https://babashka.org/)

# Usage
- Clone or download the repo
- Go to resources folder and rename env.sample.edn to env.edn
- Update the file according to your smtp config and preferences

```clojure
{:pix-key "pix-email@gmail.com"
 :total-value 34.90 ;total money being splited
 :debug true ;this defines whether it is going to actually send the e-mails or just print it
 :smtp {:host "smtp.gmail.com"
        :port 587
        :username "username@gmail.com"
        :password "passwordhere"
        :subject "Pague o Youtube Premium {{name}}"
        :from "username@gmail.com"}
 :payers [{:name "Ronaldinho"
           :email "ronaldinho@gmail.com"
           :shares 2} ; for how many people it is paying for
          {:name "Pelé"
           :email "pele@gmail.com"
           :shares 2}          
          {:name "lelele"
           :email "lelele@gmail.com"
           :shares 1}]}
```

- You can update the e-mail template in resoureces/template.html
- To run the script use that command in the root folder
```bb -m app```
