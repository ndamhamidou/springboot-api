# springboot-api

Technical test carried out by HAMIDOU NDAM Marcelin for recruitment for the position of Java developer

When starting the application, the following data is created and stored in the database:

laptops:                                                        ram:

name        ||  quantity     ||  price                          name
hp              10               5500                           ram1
acer            5                5250                           ram2
dell            7                5100                           ram3
compaq          10               4600                           ram4
asus            2                5200                           ram5
toshiba         6                6000

to test the application, use the following endpoint 
http://localhost:8080/online-store/api/purchase/
with the following parameters
{
"orderLaptopItemsDtoList": [
        {
            "modelName": "toshiba",
            "price": "5500",
            "quantity": 2
        },
        {
            "modelName": "dell",
            "price": "6100",
            "quantity": 3
        }
    ]
}

Upon receipt of the request, if the products requested exist in the database 
and if the quantities requested are available, the order is placed and the 
quantities of products available in the database are updated.
Records of the order are send to an external invoicing service and an email
notification is sent to the user with the names of the laptop models ordered and
compatible RAM memory cards.
A notification is also sent with the names of the laptop models and compatible
RAM memory card through RabbitMQ.
Otherwise, an error is returned.

