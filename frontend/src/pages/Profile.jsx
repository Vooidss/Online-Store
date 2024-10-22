import React, { useState, useEffect } from 'react';
import OrderExecutionComponent from '../components/OrderExecutionComponent'
import LoadingComponent from '../components/LoadingComponent'
import StatusComponent from '../components/StatusComponent'

export default function Profile() {
    const [user, setUser] = useState(null);
    const [isLoading, setLoading] = useState(false);
    const [status, setStatus] = useState(false);
    const [isLoaded, setLoaded] = useState(false);

    useEffect(() => {
        if (isLoaded) {
            const timer = setTimeout(() => {
                setLoaded(false);
                window.location.reload()
            }, 1500);

            return () => clearTimeout(timer);
        }
    }, [isLoaded]);

    useEffect(() => {
        try {
            const storedUser = localStorage.getItem('user');
            if (storedUser) {
                const parsedUser = JSON.parse(storedUser);
                if (parsedUser) {
                    setUser(parsedUser);
                }
            }
        } catch (error) {
            console.error('Ошибка при получении данных пользователя:', error);
        }
    }, []);

    if (!user) {
        return <div className="profile-main-window">
            <div className="profile-main-widow__profile">
                <h1>Вы ещё не вошли</h1>
            </div>
        </div>;
    }

    async function updateUser(){
        const token  = localStorage.getItem('token')
        console.log(JSON.stringify(user))

        await fetch('http://localhost:8060/user/update',{
            method: 'PATCH',
            headers:{
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(user)
        })
            .then(require => require.json())
            .then(data => {
                console.log(data);
                setLoading(false)
                setLoaded(true)
                if(data.code === 200) {
                    localStorage.setItem('user', JSON.stringify(data.user))
                    setStatus(true)
                }else{
                        setStatus(false)
                }
            })
            .catch(error => console.error(error))
    }

    return (
        <div className="profile-main-window">
            <LoadingComponent isHidden={isLoading}/>
            <StatusComponent
                status={status}
                isLoading={isLoading}
                isLoaded={isLoaded}
                textAccepted="Данные успешно обновлены"
                textRefused = "Ошибка обновления данных"
            />
            <div className="profile-main-widow__profile">
                <h1 id = "my_data">Мои данные</h1>
                <div className="profile-main-window__profile__form">
                    <div className="profile-main-window__profile__form__inputs">
                        <OrderExecutionComponent
                            text = "Имя"
                            value={user.name}
                            setValue={(value) =>
                                setUser((prev) => ({ ...prev, name: value }))}
                        />
                        <OrderExecutionComponent
                            text = "Фамилия"
                            value={user.secondname}
                            setValue={(value) =>
                                setUser((prev) => ({ ...prev, secondname: value }))}
                        />
                        <OrderExecutionComponent
                            text = "Возраст"
                            setValue={(value) =>
                                setUser((prev) => ({ ...prev, age: value }))}
                            value={user.age}
                            isDigit={true}
                        />
                    </div>
                    <div className="profile-main-window__profile__form__radio">
                        <fieldset className="profile-main-window__profile__form__radio__fieldset">
                            <legend className="profile-main-window__profile__form__radio__legend">Ваш пол</legend>

                            <label className="profile-main-window__profile__form__radio__label">
                                <input
                                    className="real-radio"
                                    type="radio"
                                    name="sex"
                                    checked={user.sex === 'male'}
                                    onChange={() => setUser((prev) => ({ ...prev, sex: 'male' }))}
                                />
                                <span className="custom-radio"></span>
                                <span>Мужской</span>
                            </label>

                            <label className="profile-main-window__profile__form__radio__label">
                                <input
                                    className="real-radio"
                                    type="radio"
                                    name="sex"
                                    checked={user.sex === 'female'}
                                    onChange={() => setUser((prev) => ({ ...prev, sex: 'female' }))}
                                />
                                <span className="custom-radio"></span>
                                <span>Женский</span>
                            </label>
                        </fieldset>
                    </div>
                    <div className="profile-main-window__profile__form__email">
                        <label>Email</label>
                        <p>{user.email}</p>
                    </div>
                    <div className="profile-main-window__profile__form__phone">
                        <OrderExecutionComponent
                            text="Телефон"
                            setValue ={(value) =>
                            setUser((prev) => ({ ...prev, phone: value }))}
                            isDigit = {true}
                            value={user.phone}
                            width = '300px'
                        />
                    </div>
                    <button
                        className="profile-main-window__profile__form__save"
                        onClick = {updateUser}
                    >
                        Сохранить
                    </button>
                </div>
            </div>
        </div>
    );
}
