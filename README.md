# If it's your first time to download this project:

(Ensure you have git installed on your machine with SSH setup), or you can clone by HTTPS

SSH:

```
git clone git@github.com:Devininthelab/sc2002-hospital-management-system.git
```

HTTPS:

```
git clone https://github.com/Devininthelab/sc2002-hospital-management-system.git
```

-   Then also follow the steps below to update your local copy with the latest changes.

# If you have cloned the project previously, you can update your local copy by running:

-   Make sure you are on the main branch before you pull the latest changes

```
cd sc2002-hospital-management-system
git checkout main
git status  -> check you are at main branch
git pull origin main
```

-   Then create a new branch with format i.e `git checkout -b <yourbranch>`. This will create a new branch and switch to it. Here I am creating a new branch for coding `user.class`, so the branch should be named `user`.

```
git checkout -b <yourbranch>
```

-   After you have made your changes, you can add them to the staging area by running:

```
git add .
git commit -m "Your commit message"
```

-   Then push your changes to your branch by running:

```
git push origin <yourbranch>
```

-   Then you open github, it will create a new branch and a pull request in Github repo. You can then merge the changes to the main branch, if it does not have any conflicts.
