import argparse
import json
import sys
import os
import re
from itertools import count
from decimal import Decimal
import commands

def parse_args(argv, version, step):
    parser = argparse.ArgumentParser('deploy project to github, travis-ci and coverageall')
    parser.add_argument('-v', dest='base_version', default=version)
    parser.add_argument('-p', dest='version_step', default=step)
    parser.add_argument('-c', dest='comments', default=None)
    parser.add_argument('-j', dest='jump', default=False)

    if argv is not None: 
        return parser.parse_args(argv)

    return parser.parse_args()

def generate_version_code(args, version, info):
    if args.base_version != version:
        version = args.base_version

    version_count = count(start=Decimal(version), step=Decimal(args.version_step))
    version_count.next()
    version = format(version_count.next(), '0.2f')

    version_number = 'v{}'.format(version)

    with open('./version.json', 'w') as f:
        info['version'] = float(version)
        json.dump(info, f)

    return version_number

def change_readme_travis(version_code):
    str = ''
    with open('./README.md', 'r') as f:
        for line in f.readlines():
            line = re.sub(
                r'<version>.*</version>', 
                '<version>{}</version>'.format(version_code),
                line
            )
            str += line

    with open('./README.md', 'w') as f:
        f.write(str)

    str = ''
    with open('./.travis.yml', 'r') as f:
         for line in f.readlines():
            line = re.sub(
                r'- git tag -f \".*\"', 
                '- git tag -f "{}"'.format(version_code),
                line
            )
            str += line       
    with open('./.travis.yml', 'w') as f:
        f.write(str)

def main(argv=None):
    info = None
    with open('./version.json', 'r') as f:
        info = json.load(f)

    if info is None:
        sys.exit(0)

    version = float(info['version'])

    print 'version {}'.format(
        version,
    )

    args = parse_args(argv, version, 0.01)

    if args.comments is None:
        print 'args comments could not be None !'
        sys.exit(0)

    version_code = 'v{}'.format(version)
    if not args.jump:
        version_code = generate_version_code(args, version, info)

    # change mvn version
    os.system('mvn versions:set -DnewVersion={}'.format(version_code))
    # change read me version
    print '<version>v{}</version>'.format(version)
    change_readme_travis(version_code)
    # git update
    os.system('git add -A')
    os.system('git commit -am "{}"'.format(args.comments))
    os.system('git push origin master')

if __name__ == '__main__':
    main()