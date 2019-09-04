# RedditScrapperTelegramBot

This is a Telegram Bot that answers the command:  
`/NothingToDo [subreddit1;subreddit2;subreddit3...]`  
with the threads that are trending at the moment at the requested subreddits.

To configure it: 

1- Inside the `reddit-scrapper/src/main/resources` folder edit the file `application-telegram.properties` with following properties:  
```text
telegram.bot.username=your_bot_name
telegram.bot.token=your_bot_token
```

To run it: 

1- Compile using `./gradlew clean build`

2- Run it with  
`java -jar reddit-scrapper-0.0.1-SNAPSHOT.jar` or  
`./gradlew bootRun`