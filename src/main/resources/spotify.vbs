Set WshShell = WScript.CreateObject("WScript.Shell")
Comandline = "C:\Users\Max\AppData\Roaming\Spotify\Spotify.exe"
WScript.sleep 500
CreateObject("WScript.Shell").Run("spotify:user:4dng9op4dmvcbhjf48uamyx9u:playlist:playlist:1nwrUmj9GfXU6PVXiBgRAG")
WScript.sleep 3000
WshShell.SendKeys " "