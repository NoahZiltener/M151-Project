## Projektbeschrieb

### Um was geht es im Projekt?
Das Thema, für das ich mich entschieden, habe ist ein Auto-Auktionshaus. In der Applikation können Autos versteigert werden. Auf ein Auto kann geboten werden oder mit einem Direktkaufpreis gekauft werden. Es gibt auch eine Ablaufzeit für die Aktion nach dem das Auto an den Höchst bietenden verkauft wird.

### Wer sind die Personengruppe?

| Bild | Name | Gruppe | Beschreibung |
| ----- | ----- | ----- |:-------------:|
| ![image](https://user-images.githubusercontent.com/24568118/115667298-f5646380-a345-11eb-86ce-2bcc0d8fcd4d.png)| Charles | Auktions-Admin | Charles ist 60 Jahre alt. Er ist ein Admin auf der Auktions-Platform. Sein Job ist es die Platform zu administrieren. Er soll neue Benutzer anlegen, Auktionen bearbeiten und löschen können.|
| ![image2](https://user-images.githubusercontent.com/24568118/115667668-64da5300-a346-11eb-8118-b6014306d5f0.png) | Eric | Auktionator | Eric ist 45 Jahre alt. Er hat vor kurzem ein Oldtimer geerbt. Da er das Geld gut gebrauchen kann und nicht so viel von Autos hält möchte er es versteigern.|
| ![image3](https://user-images.githubusercontent.com/24568118/115667684-69067080-a346-11eb-861a-3c9f26740d8b.png) | Ali | Käufer | Ali ist 27 Jahre alt. Er interessiert sich sehr für alte Autos. Er möchte auf der Platform seinen ersten Oldtimer kaufen. |

### Wie soll die Applikation von diesen Nutzern genutzt werden?

| ID | User-Story  | Status |
| -----|:-------------:| ---|
| US01 | Als Käufer möchte ich alle Auto Auktionen sehen, um einen guten Überblick auf die Auktion zu bekommen. |✅|
| US02 | Als Käufer möchte ich Details einer Auktion sehen, um mich über die Auktion zu informieren. |✅|
| US03 | Als Käufer möchte ich auf eine Auktion ein Gebot setzten können, um das höchste gebot zu haben. |✅|
| US04 | Als Käufer möchte ich ein Auto für den Direktkaufpreis kaufen, um die Auktion sofort zu Gewinnen. |✅|
| US05 | Als Auktionator möchte ich eine Auktion erstellen, um ein Auto zu verkaufen. |✅|
| US06 | Als Auktionator möchte ich alle meine Auktionen sehen, um mich über meine Auktionen zu informieren. |✅|
| US07 | Als Auktions-Admin möchte ich Auktionen bearbeiten können, um falsche Angaben zu korrigieren. |
| US08 | Als Auktions-Admin möchte ich neue Käufer und Auktionator erfassen können, damit des Auktionshauses grösser wird. |✅|

### Welche Technologien werden eingesetzt?

Für das Projekt habe ich mich entschieden [Java](https://www.java.com/) als Programmiersprachen zu benutzen. Für die Verbindung mit der Datenbank möchte ich das Framework [Spring](https://spring.io) verwenden. Ich möchte auch ein Caching-System einsetzen. Für das werde ich [Redis](https://redis.io) verwenden. Die Datenban die ich verwenden will habe ich mich für eine Postgres Datenbank entschieden. Die Datenbank und Redis wird in einem Docker-Container laufen.
