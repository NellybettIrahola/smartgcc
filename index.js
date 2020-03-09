const { app, BrowserWindow, ipcMain } = require('electron')

const path = require('path')

require('electron-reload')(__dirname, {
});

var child = require('child_process').execFile;

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let win

function createWindow () {
  // Create the browser window.
  win = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      nodeIntegration: true
    }
  })

  // and load the index.html of the app.
  win.loadFile('index.html')
  //ipcMain.on will receive the “btnclick” info from renderprocess 
  ipcMain.on("btnclick",function (event, arg) {
    console.log("btn-click?!" + arg)
    win.loadFile('tabs.html')
  });

  ipcMain.on("gcc-build",function (event, arg) {
    console.log("gcc-build?!" + arg)
    var executablePath = "C:\\msys64\\mingw64\\bin\\gcc.exe";
    var parameters = ["-dumpversion"];
    child(executablePath, parameters, function(err, data) {
        if(err){
          console.error(err);
          return;
        }
        data_str = data.toString()
        console.log(data_str);
        event.sender.send("gcc-build-finished", data_str); 
    });  
  });

  // Open the DevTools.
  // win.webContents.openDevTools()

  // Emitted when the window is closed.
  win.on('closed', () => {
    // Dereference the window object, usually you would store windows
    // in an array if your app supports multi windows, this is the time
    // when you should delete the corresponding element.
    win = null
  })
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', () => {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  // On macOS it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (win === null) {
    createWindow()
  }
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.

require('./applicationMenu.js')
