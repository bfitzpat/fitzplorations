
from("telegram:bots/889842995:AAFkUBy4tO5mii6xZli1z_2rLi0_p05Y9ds?chatId=896669017")
    .choice()
        .when()
            .simple('${in.body} != null')
            .to("direct:response1")

from("direct:response1")
    .log('Incoming message from Telegram is ${in.body}')
    .setBody()
        .simple('Why did you say \"${in.body}\"?')
        .to("telegram:bots/889842995:AAFkUBy4tO5mii6xZli1z_2rLi0_p05Y9ds?chatId=896669017")
