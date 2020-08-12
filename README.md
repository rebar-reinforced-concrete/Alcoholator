#Dt'Ròjdíyliiðwrsslaavfájtung ssödäl Dt'Alkoholjátorniif _[Alcoholator development plan]_
---  
##Dt'Trelloøbwrgsstág _[Trello board]_ https://trello.com/b/LjBJjs5P/the-plan
---  
## immediate fixes **deadline**: 4 days(13 aug - 17 aug @ 23:50)  
- [ ] refactoring  
    - Log.d() **MW**  
    - Remove LiveData from repo **MW**  
    - State reevaluation **MW**  
    - Detekt\ktlint **TG**  
    - ```.editorconfig``` **TG**  
    - Navigation manager **TG**  
    - WorkManager (optional)  
    - Landscape orientation  
    - Drink deletion  
- [ ] database restructurazation **MK**  
- [ ] enum\dict madness **MK**  
- [ ] solid principles **TG**  
- [ ] clean architecture  **TG**  
- [ ] ui\ux redesign (references: _tinder_, _xiaomi default apps_) **MW\TG**  
- [ ] name **MW\TG**  
- [ ] main icon and custom icon pack **MW\TG**  
- [ ] branding and animation **MW\TG**  
- [ ] custom notifications sfx **MK**  
- [ ] **TESTING**  
- [ ] UI-state graph  

---  
## network  
- [ ] retrofit  
    - open API(drink types etc.) **TG**  
    - proprietary API(server) **TG(arch), MW(impl)**  
- [ ] server(db) **MK**  
- [ ] firestore crashlytics  
- [ ] drink pictures, drunk selfies(Glide) **TG**  

---  
## features  
- [ ] google maps integration  
    - shows positional drinking history  
    - shows drinking buddies in relation to parties\locations  
- [ ] competetive mode  
    - User selects buddies they are competing against and, by the end, user with the highest concentration wins  
- [ ] Live drinking mode  
    - User activates the LDM, selects the current drink and typical volume, then a presistent notification appears in which the user can quickly tap a button and add the LDM-selected drink.  
- [ ] ML-based coefficients adjustment  
    - User can specify, that they are sober prior to the calculated time, that triggers the personalized formula coefficient adjustment, thus making further calculations more precise.  
- [ ] Geolocation-based drinking buddies conglomeration  
    - During the party, a user can select that they are responsible for drinking logging: upon selection they'll see all buddies nearby and will be able to log drinks on their behalf.  
- [ ] Bad habits coefficient adjustment  
- [ ] Gyroscope-based game to test User's coordination  
- [ ] _I barfed_ button  
- [ ] Statistics  
    - google weather-esque graphs of  
        - volume over time  
        - concentration over time  
        - parties over the week (reference _pedometer app_)  
    - gross total  
    - favourite drinking buddy  
    - favourite drink  
    - favourite type  
    - preferable volume  
- [ ] sharing  
    - invite referal links (future: _remove ads for a week_)  
    - share current state  
- [ ] **optional**: blocking of selected apps.  
- [ ] send periodical reminders (approx. freq. once a week of being sober)  
