# -*- mode: python -*-

block_cipher = None


a = Analysis(['alpha_main.py'],
             pathex=['C:\\Users\\siotman\\Desktop\\Projects\\sju-paper-scraper-app'],
             binaries=[],
             datas=[],
             hiddenimports=[
                 "requests",
                 "pandas",
                 "bs4",
                 "robobrowser",
                 "multiprocessing"
                 ],
             hookspath=[],
             runtime_hooks=[],
             excludes=[],
             win_no_prefer_redirects=False,
             win_private_assemblies=False,
             cipher=block_cipher,
             noarchive=False)
pyz = PYZ(a.pure, a.zipped_data,
             cipher=block_cipher)
exe = EXE(pyz,
          a.scripts,
          a.binaries,
          a.zipfiles,
          a.datas,
          [],
          name='alpha_main',
          debug=False,
          bootloader_ignore_signals=False,
          strip=False,
          upx=True,
          runtime_tmpdir=None,
          console=True )
