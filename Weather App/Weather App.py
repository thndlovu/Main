from tkinter import *
import tkinter as tk
from PIL import Image, ImageTk
from geopy.geocoders import Nominatim
from tkinter import ttk,messagebox
from timezonefinder import TimezoneFinder
from datetime import datetime
import requests
import pytz

root = Tk()
root.title("Weather App")
root.geometry("900x500+300+200")
root.resizable(False,False)
root.configure(bg = "#636363")

def getWeather():

    try:
        city = textfield.get()

        geolocator = Nominatim(user_agent = "geopiExercises")
        location = geolocator.geocode(city)
        obj = TimezoneFinder()
        result = obj.timezone_at(lng = location.longitude, lat = location.latitude)

        home = pytz.timezone(result)
        local_time = datetime.now(home)
        current_time = local_time.strftime("%I:%M %p")
        clock.config(text = current_time)
        name.configure(text = "CURRENT WEATHER")

        #weather
        api = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=d59812e48deac9103e4cccac56600df3"

        json_data = requests.get(api).json()
        condition = json_data['weather'][0]['main']
        description = json_data['weather'][0]['description']
        temp = int(json_data['main']['temp']-273.15)
        pressure = json_data['main']['pressure']
        himidity = json_data['main']['humidity']
        wind = json_data['wind']['speed']

        t.config(text = (temp, "°"))
        c.config(text = (condition, "|" "FEELS", "LIKE", temp, "°"))

        w.config(text = str(wind) + "mi/h")
        h.config(text = str(himidity) + "%")
        d.config(text = description)
        p.config(text = str(pressure) + " hPa")

    #exception handling
    except Exception as e:
        messagebox.showerror("Weather App", "Invalid Entry. Please Try Again.")
                


#search box
image = Image.open("/Users/blacboy26/Desktop/searchbar10.png")
image = image.resize((470,350), Image.Resampling.LANCZOS)

Search_image = ImageTk.PhotoImage(image)
myImage = Label(image = Search_image, bg = "#636363")
myImage.place(x = 10, y = -130)

textfield = tk.Entry(root, justify = "center", width = 15, font = ("Calibri", 30, "bold"),
                     bg = "#636363", bd = 0, fg = "#B7B9A6", highlightthickness = 0)
textfield.place(x = 40, y = 28)
textfield.focus()

#search icon -> click after search to return results
icon = Image.open("/Users/blacboy26/Desktop/search.png")
icon = icon.resize((62,60), Image.Resampling.LANCZOS)

Search_icon = ImageTk.PhotoImage(icon)
myImage_icon = Button(image = Search_icon, bg = "#636363", borderwidth = 0, cursor = "hand2", command = getWeather)
myImage_icon.place(x = 416, y = 15)

#Logo
logoImage = Image.open("/Users/blacboy26/Desktop/weather.png")
logoImage = logoImage.resize((250,250), Image.Resampling.LANCZOS)


Logo_image = ImageTk.PhotoImage(logoImage)
logo = Label(image = Logo_image, bg = "#636363")
logo.place(x = 70, y = 130)


#time
name = Label(root, font = ("Calibri", 20, "bold"), bg = "#636363", fg = "#B7B9A6")
name.place(x = 30, y = 100)
clock = Label(root, font = ("Calibri", 18), bg = "#636363", fg = "#B7B9A6")
clock.place(x = 30, y = 130)


#condition labels
label1 = Label(root, text = "WIND", font = ("Calibri", 15, 'bold'), fg = "#B7B9A6", bg = "#636363")
label1.place(x = 180, y = 410)

label2 = Label(root, text = "HUMIDITY", font = ("Calibri", 15, 'bold'), fg = "#B7B9A6", bg = "#636363")
label2.place(x = 285, y = 410)

label3 = Label(root, text = "DESCRIPTION", font = ("Calibri", 15, 'bold'), fg = "#B7B9A6", bg = "#636363")
label3.place(x = 430, y = 410)

label4 = Label(root, text = "PRESSURE", font = ("Calibri", 15, 'bold'), fg = "#B7B9A6", bg = "#636363")
label4.place(x = 610, y = 410)

#condition data labels
t = Label(font = ("Calibri", 80, "bold"), bg = "#636363", fg = "#B7B9A6")
t.place(x = 315, y = 155)
c = Label(font = ("Calibri", 20, 'bold'), bg = "#636363", fg = "#B7B9A6")
c.place(x = 315, y = 240)

w = Label(text = "...", font = ("Calibri", 20, "bold"), fg = "#B7B9A6", bg = "#636363")
w.place(x = 180, y = 430)

h = Label(text = "...", font = ("Calibri", 20, "bold"), fg = "#B7B9A6", bg = "#636363")
h.place(x = 285, y = 430)

d = Label(text = "...", font = ("Calibri", 20, "bold"), fg = "#B7B9A6", bg = "#636363")
d.place(x = 430, y = 430)

p = Label(text = "...", font = ("Calibri", 20, "bold"), fg = "#B7B9A6", bg = "#636363")
p.place(x = 610, y = 430)


#start the event loop i.e., keeps program running and responsive for user interactions
root.mainloop()

