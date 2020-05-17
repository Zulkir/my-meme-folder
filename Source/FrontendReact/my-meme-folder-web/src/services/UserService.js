import axios from "axios";

class UserService {
    userInfo = null;
    changeHandlers = [];

    getUserInfo() {
        return this.userInfo;
    }

    refreshUserInfo() {
        axios.get("/api/user-info")
            .then(res => {
                if (res.status === 200)
                    this.setUserInfo(res.data);
            })
            .catch(e => {
            });
    }

    setUserInfo(userInfo) {
        this.userInfo = userInfo;
        this.changeHandlers.forEach(h => h(userInfo));
    }

    subscribeToChange(handler) {
        this.changeHandlers.push(handler);
    }

    unsubscribeFromChange(handler) {
        this.changeHandlers.splice(this.changeHandlers.indexOf(handler), 1);
    }
}

const Service = new UserService();
export default Service;