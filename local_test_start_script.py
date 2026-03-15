# A script to start and stop the frontend and backend services for local testing
# This is strictly to enable fast start instead of typing multiple commands

import subprocess
import sys
import json
from pathlib import Path


SCRIPT_DIR = Path(__file__).resolve().parent
PID_FILE = SCRIPT_DIR / "dev_pids.json"

FRONTEND_DIR = r"you_should_replace_this_with_your_frontend_directory"
BACKEND_DIR = r"you_should_replace_this_with_your_backend_directory"


def start():
    print("Starting services...")

    npm = subprocess.Popen("npm run dev", cwd=FRONTEND_DIR, shell=True)

    spring = subprocess.Popen("mvn spring-boot:run", cwd=BACKEND_DIR, shell=True)

    pids = {
        "npm": npm.pid,
        "spring": spring.pid
    }

    with open(PID_FILE, "w") as f:
        json.dump(pids, f)

    print(f"Frontend PID: {npm.pid}")
    print(f"Backend PID: {spring.pid}")
    print("Services started.")


def stop():
    if not PID_FILE.exists():
        print("No running services found.")
        return

    with open(PID_FILE) as f:
        pids = json.load(f)

    for name, pid in pids.items():
        try:
            subprocess.run(["taskkill", "/PID", str(pid), "/F"])
            print(f"Stopped {name} ({pid})")
        except Exception as e:
            print(f"Could not stop {name}: {e}")

    PID_FILE.unlink()


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python dev_runner.py [start|stop]")
        sys.exit(1)

    command = sys.argv[1]

    if command == "start":
        start()
    elif command == "stop":
        stop()
    else:
        print("Unknown command")
