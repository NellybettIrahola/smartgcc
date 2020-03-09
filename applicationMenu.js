const { app, Menu } = require('electron')

const isMac = process.platform === 'darwin'

const template = [
  // { role: 'appMenu' }
  ...(isMac ? [{
    label: app.name,
    submenu: [
      { role: 'about' },
      { type: 'separator' },
      { role: 'services' },
      { type: 'separator' },
      { role: 'hide' },
      { role: 'hideothers' },
      { role: 'unhide' },
      { type: 'separator' },
      { role: 'quit' }
    ]
  }] : []),
  // { role: 'fileMenu' }
  {
    label: 'File',
    submenu: [
      {
        label: 'Open Project...',
        click: async () => {
          console.log("Open Project...")
        }
      },
      {
        label: 'Save Project',
        click: async () => {
          console.log("Save Project")
        }
      },
      {
        label: 'Save Project As...',
        click: async () => {
          console.log("Save Project As...")
        }
      },
      {
        label: 'Autosave Project',
        click: async () => {
          console.log("Autosave Project")
        }
      },
      {
        label: 'Close Project',
        click: async () => {
          console.log("Close Project")
        }
      },
      isMac ? { role: 'close' } : { role: 'quit' }
    ]
  },
  // { role: 'editMenu' }
  {
    label: 'Edit',
    submenu: [
      { role: 'undo' },
      { role: 'redo' },
      { type: 'separator' },
      { role: 'cut' },
      { role: 'copy' },
      { role: 'paste' },
      ...(isMac ? [
        { role: 'pasteAndMatchStyle' },
        { role: 'delete' },
        { role: 'selectAll' },
        { type: 'separator' },
        {
          label: 'Speech',
          submenu: [
            { role: 'startspeaking' },
            { role: 'stopspeaking' }
          ]
        }
      ] : [
        { role: 'delete' },
        { type: 'separator' },
        { role: 'selectAll' }
      ])
    ]
  },
  // { role: 'viewMenu' }
  {
    label: 'View',
    submenu: [
      { role: 'reload' },
      { role: 'forcereload' },
      { role: 'toggledevtools' },
      { type: 'separator' },
      { role: 'resetzoom' },
      { role: 'zoomin' },
      { role: 'zoomout' },
      { type: 'separator' },
      { role: 'togglefullscreen' }
    ]
  },
  // { role: 'windowMenu' }
  {
    label: 'Window',
    submenu: [
      { role: 'minimize' },
      { role: 'zoom' },
      ...(isMac ? [
        { type: 'separator' },
        { role: 'front' },
        { type: 'separator' },
        { role: 'window' }
      ] : [
        { role: 'close' }
      ])
    ]
  },
  {
    role: 'help',
    submenu: [
      {
        label: 'Manual',
        click: async () => {
          console.log("Manual")
        }
      },
      {
        label: 'Tips on Startup',
        click: async () => {
          console.log("Tips on Startup")
        }
      },
      { type: 'separator' },
      {
        label: 'SmartGCC Homepage',
        click: async () => {
          console.log("SmartGCC Homepage")
        }
      },
      {
        label: 'SmartGCC Community',
        click: async () => {
          console.log("SmartGCC Community")
        }
      },
      { type: 'separator' },
      {
        label: 'Send Feedback',
        submenu: [
          {
            label: 'General Feedback',
            click: async () => {
              console.log("General Feedback")
            }
          },
          {
            label: 'Report an Issue',
            click: async () => {
              console.log("Report an Issue")
            }
          }        
        ]
      },
      { type: 'separator' },
      {
        label: 'Learn More',
        click: async () => {
          const { shell } = require('electron')
          await shell.openExternal('https://electronjs.org')
        }
      }
    ]
  }
]

const menu = Menu.buildFromTemplate(template)
Menu.setApplicationMenu(menu)
