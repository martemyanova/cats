# Cats (interview task)
An app that allows the user to search for cats, present the results and allows them to find out more about a specific Cat.

<img width="366" alt="Screenshot 2021-02-21 at 22 16 44" src="https://user-images.githubusercontent.com/16405143/108640592-875d0680-7492-11eb-8628-63f27cd9c8c7.png">

The app allows to search by breed name only. 
It use a breed search get-request to retrieve breeds and takes only first result (for simplicity). Then it sends second request to retrieve cats images selected by breedId (results are limited to 50 items) and shows results on the main screen.
