# Test-App
Recently, Hacker News released their initial version of API, allowing mobile developers for the first time a way to retrieve content through REST API without having to timidly scrape content from its website.
The candidate is required to utilize this API to develop a simple Hacker News reader app. The app should contain at least 2 screens:
	Home screen with list of most popular news (from topstories endpoint). The items should be displayed in order. For each item: display title, author handle, points and time from present, and a button to open URL in external browser. The screen should have a 'pull to refresh' feature to allow users to manually refresh the list. Refer to https://news.ycombinator.com/ for example.
	Details screen (accessible by clicking an item on home screen): display latest 10 comments (content, author handle, time), for each comment display latest reply (content, author handle, title) if any. Refer to https://news.ycombinator.com/item?id=8422599 for example. If easier, it is okay to display unlimited hierarchy of comments.

