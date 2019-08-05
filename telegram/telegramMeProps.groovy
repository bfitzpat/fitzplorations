
from("telegram:bots/{{telegram.APIKey}}?chatId={{telegram.chatI}}")
    .choice()
        .when()
            .simple('${in.body} != null')
            .to("direct:response1")

from("direct:response1")
    .log('Incoming message from Telegram is ${in.body}')
    .setBody()
        .simple('Why did you say \"${in.body}\"?')
        .to("telegram:bots/{{telegram.APIKey}}?chatId={{telegram.chatI}}")
