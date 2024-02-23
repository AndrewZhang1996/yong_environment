import { app, BrowserWindow } from 'electron'
// import '../renderer/store'

/**
 * Set `__static` path to static files in production
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-static-assets.html
 */
// if (process.env.NODE_ENV !== 'development') {
//     global.__static = require('path').join(__dirname, '/static').replace(/\\/g, '\\\\')
// }

let mainWindow

// const winURL = process.env.NODE_ENV === 'development' ?
//     `http://localhost:3001` :
//     `http://124.223.109.244/`

const winURL = `http://124.223.109.244/`

function createWindow() {
    /**
     * Initial window options
     */
    mainWindow = new BrowserWindow({
        center: true,
        minWidth: 1010,
        minHeight: 400,
        height: 500,
        useContentSize: true,
        width: 1010
    })

    mainWindow.loadURL(winURL)

    mainWindow.on('closed', () => {
        mainWindow = null
    })
    
    // 控制等比缩放
    mainWindow.on('will-resize', resizeWindow)
}

app.on('ready', createWindow)

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        app.quit()
    }
})

app.on('activate', () => {
    if (mainWindow === null) {
        createWindow()
    }
})


function resizeWindow(event, newBounds) {
  const win = event.sender
  event.preventDefault() // 拦截，使窗口先不变
  const currentSize = win.getSize()
  const widthChanged = currentSize[0] !== newBounds.width // 判断是宽变了还是高变了，两者都变优先按宽适配
  // ! 虽然搞不懂为何有1px偏差，但是可以解决问题(Windows 10)
  if (widthChanged) {
    win.setContentSize(newBounds.width - 1, parseInt(newBounds.width / aspectRatio + 0.5) - 1)
  } else {
    win.setContentSize(parseInt(aspectRatio * newBounds.height + 0.5) - 1, newBounds.height - 1)
  }
}

/**
 * Auto Updater
 *
 * Uncomment the following code below and install `electron-updater` to
 * support auto updating. Code Signing with a valid certificate is required.
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-electron-builder.html#auto-updating
 */

/*
import { autoUpdater } from 'electron-updater'

autoUpdater.on('update-downloaded', () => {
  autoUpdater.quitAndInstall()
})

app.on('ready', () => {
  if (process.env.NODE_ENV === 'production') autoUpdater.checkForUpdates()
})
 */