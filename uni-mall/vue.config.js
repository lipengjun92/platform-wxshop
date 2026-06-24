const fs = require('fs')
const path = require('path')

function copyDirectory(sourceDir, targetDir) {
  if (!fs.existsSync(sourceDir)) {
    return
  }

  fs.mkdirSync(targetDir, { recursive: true })

  fs.readdirSync(sourceDir, { withFileTypes: true }).forEach(entry => {
    const sourcePath = path.join(sourceDir, entry.name)
    const targetPath = path.join(targetDir, entry.name)

    if (entry.isDirectory()) {
      copyDirectory(sourcePath, targetPath)
      return
    }

    fs.copyFileSync(sourcePath, targetPath)
  })
}

function copyFile(sourceFile, targetFile) {
  if (!fs.existsSync(sourceFile)) {
    return
  }

  fs.mkdirSync(path.dirname(targetFile), { recursive: true })
  fs.copyFileSync(sourceFile, targetFile)
}

class CopySkillsPlugin {
  apply(compiler) {
    compiler.hooks.afterEmit.tap('CopySkillsPlugin', () => {
      const outputDir = compiler.options.output.path
      copyDirectory(path.resolve(__dirname, 'skills'), path.join(outputDir, 'skills'))
      copyFile(path.resolve(__dirname, 'agent/AGENTS.md'), path.join(outputDir, 'agent/AGENTS.md'))
      copyFile(path.resolve(__dirname, 'agent/page-meta.json'), path.join(outputDir, 'agent/page-meta.json'))
    })
  }
}

module.exports = {
  configureWebpack: () => {
    if (process.env.UNI_PLATFORM !== 'mp-weixin') {
      return {}
    }

    return {
      plugins: [new CopySkillsPlugin()]
    }
  }
}
