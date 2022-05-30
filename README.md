# hackathon2022
app that aims to help new immigrants who experience loneliness and adjustment difficulties when arriving in a foreign country, the app will help them get to know other immigrants from different countries who experience the same difficulties and understand them and will encourage integration in the population.

The app will be able to identify the type of immigrant, and based on the built-in algorithm offer them meetings / help with a minimum of subscribers, taking into account the immigrant's demand for a certain thing and personal preferences such as: city of residence, age range, languages spoken by the immigrant, etc.

<img width="280" alt="logo_white" src="https://user-images.githubusercontent.com/106392721/170930817-b77d3efb-0272-4124-8fe1-838b03e2599f.png">

### Registration and login through 3 interfaces:
**Upcoming interface:** Opening meetings with suggestions of meeting places according to the type of meeting, joining existing meetings, chat group before each existing meeting, forum to raise questions and problems for users to answer.
**Volunteer interface:** Offer help and advice, perform appropriate activities.
**Really native of the country:** joining meetings to integrate with the immigrants.


#### The app will allow a new immigrant:
- Open a new meeting that will be presented to immigrants with similar characteristics to his, choose whether to include the natives or only among the immigrants, type of meeting, date and time, language of meeting according to languages the immigrant indicated he knows in registration, meeting place by list defined by type of meeting and area of residence of the immigrant.
- Join an existing meeting with immigrants with similar characteristics to his.
- Watch the future meetings signed up for it.
- Consult the forum about the problems that arise and get a response from people like him and in a reliable way.


#### Types of meetings in the app: (There will be meetings of formation between the immigrants with or without a volunteer and there will be meetings that are more connected with the Israelis)
- Support meetings.
- Sports meetings (football, basketball, running ..).
- Israeli poetry sessions.
- Quizzes and games.
- Israeli holiday meetings and events.
- Another meeting.

## Screens in the app
### Start screen:

<img width="256" alt="opening" src="https://user-images.githubusercontent.com/106392721/170935209-abbef366-6c5d-447a-93a4-a54639f88491.png">

### opening screen:
In this screen the user selects the type of user he is and there is an explanation of each interface.

<img width="256" alt="opening2" src="https://user-images.githubusercontent.com/106392721/170935497-4b8a96ce-4410-48fc-b7dd-7764e307bb47.png">

### login screen:
- New immigrant login screen as you can see in the text listed above.
- This login screen with email and password and with firebase server.

<img width="255" alt="login" src="https://user-images.githubusercontent.com/106392721/170935642-800fea89-785e-4175-9d1d-8c5eadbeeba1.png">

### register screen:
- New immigrant registration screen as you can see in the text listed above.
- The new immigrant registration screen contains more data that the user needs to fill out. Because the app was created for new immigrants, they have the most options in the app.
- This registration screen is based on email and password and the rest of the data is stored in data for the purpose of adjusting the application that will be more relevant to the registered user.
- Registration and database with firebase server.
 
<img width="258" alt="register" src="https://user-images.githubusercontent.com/106392721/170935945-4bf9b15f-9c25-4db8-adae-2587d9ef427d.png">


### home - profile and list meetings registered:
- Information about the profile appears according to the information it is registered with, some of which the user will be able to edit.
- There is a list of meetings that the user registered for, the lock image that appears on meetings indicates that the meeting did not meet the conditions for the meeting to take place, ie it did not reach the minimum number of participants or that a volunteer is missing, an explanation of what conditions are missing. Meeting information.
- 
<img width="255" alt="home_profile_list_meetings_registered" src="https://user-images.githubusercontent.com/106392721/170936880-0c904f74-46c8-467c-9e70-16c6c36fd51d.png">

#### home - edit profile:
- Clicking on the pen in the previous image opens a dialog to select what information the user wants to edit

<img width="258" alt="home_edit_profile" src="https://user-images.githubusercontent.com/106392721/170937818-9d44c5b2-6f87-4443-9f26-37afd668843c.png">


### init meet:
- General information about the meeting for the purpose of registering for the meeting or for the purpose of explaining the meeting that is registered.

<img width="257" alt="info_meet" src="https://user-images.githubusercontent.com/106392721/170938000-e215660c-c2d5-4611-8331-a5a9bfaaf4bc.png">

### register for a new meeting:
- A screen that shows all the appointments that have been opened for registration for these appointments from today's date.
- The green check mark in the calendar shows all the scheduled appointments and clicking on it displays the appointments from the date clicked on it. Clicking on a past date displays the appointments starting from today's date.
- Each box has a sign up button but because all of these sessions are sessions I opened then this button does not appear because I automatically signed up for them from the moment they opened.
- The plus allows you to open a new meeting.
- Clicking on the box refers to more information about the meeting.

<img width="254" alt="add_meet" src="https://user-images.githubusercontent.com/106392721/170938259-7463c0a0-66eb-4a4a-8ead-548dc3e4c67d.png">

### add new meet:
- Screen for opening a new session.
- The user selects a meeting type according to a given list and after selecting a meeting type an explanation will appear below the meeting about the meeting and the list for choosing the meeting location may also change.
- The user selects the meeting language from the list of languages that the participant indicated he knows.
- The user chooses to attach the natives of the country or only the immigrants, only if he chooses to attach the natives of the country will the meeting of the natives of the country be presented to the registrar.
- The user selects the time and date of the meeting.
- The user selects the meeting location according to a given list and by clicking on "else" the user can change his choice.
- Clicking "save" will save the session and display it to users with similar properties.

<img width="257" alt="add_new_meet" src="https://user-images.githubusercontent.com/106392721/170939445-8a5e9be0-c64d-4af2-8044-bcf1860a755e.png">

### forum:
- A forum where users can consult on topics that are relevant to them.
- Clicking on one of the topics will take you to a page that will display a detail on the topic and also show the answer on that particular topic.
- Clicking Plus allows the user to open a new topic for discussion.

<img width="253" alt="forum" src="https://user-images.githubusercontent.com/106392721/170940378-9adc7df3-bb73-406c-b78e-88292488f533.png">

### forum info:
- Discussion of the topic chosen in the forum.
- The user can add his response to the topic.

<img width="254" alt="forum_info" src="https://user-images.githubusercontent.com/106392721/170940790-0510f904-4c06-4662-ab25-d82cd1861056.png">


### new forum:
- The user can open a new discussion so that it can be redirected to all users.

<img width="254" alt="forum_new" src="https://user-images.githubusercontent.com/106392721/170940797-692578c8-e109-43a6-a0b5-a0794e8a4a4b.png">


## video:

https://user-images.githubusercontent.com/106392721/170932405-54d5bbdf-db1b-4790-88c0-b4f6832bff23.mp4


#### Further development:
- A chat that will open for each meeting that has passed the conditions so that the meeting took place so that they can talk to each other before and after the meeting.
- After each meeting he will open an opinion / rating for the meeting about the people and the volunteer and the type of meeting, there will be a learning system where he will not or will offer a meeting with the same people and a volunteer and will also send notifications about "we saw you gave a good opinion for a similar meeting ..."
