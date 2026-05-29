// ─── API Configuration ────────────────────────────────────────────
const BASE_URL = 'http://127.0.0.1:8080';

// ─── Token Helpers ────────────────────────────────────────────────
const getToken = () => localStorage.getItem('token');
const getUser  = () => JSON.parse(localStorage.getItem('user') || 'null');
const setAuth  = (token, user) => {
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
};
const clearAuth = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
};

// ─── Base Fetch ───────────────────────────────────────────────────
async function api(path, options = {}) {
    const token = getToken();
    const headers = { 'Content-Type': 'application/json', ...options.headers };
    if (token) headers['Authorization'] = `Bearer ${token}`;

    const res = await fetch(`${BASE_URL}${path}`, { ...options, headers });
    const data = await res.json().catch(() => ({}));

    if (!res.ok) {
        throw new Error(data.message || `Error ${res.status}`);
    }
    return data.data !== undefined ? data.data : data;
}

// ─── Auth API ─────────────────────────────────────────────────────
const Auth = {
    register: (body) => api('/api/auth/register', { method: 'POST', body: JSON.stringify(body) }),
    login:    (body) => api('/api/auth/login',    { method: 'POST', body: JSON.stringify(body) }),
};

// ─── Hackathon API ────────────────────────────────────────────────
const Hackathons = {
    getAll:    ()     => api('/api/hackathons'),
    getOne:    (id)   => api(`/api/hackathons/${id}`),
    getUpcoming: ()   => api('/api/hackathons/upcoming'),
    create:    (body) => api('/api/hackathons', { method: 'POST', body: JSON.stringify(body) }),
};

// ─── Team API ─────────────────────────────────────────────────────
const Teams = {
    getByHackathon: (hid)  => api(`/api/teams/hackathon/${hid}`),
    getOne:         (id)   => api(`/api/teams/${id}`),
    getMyTeams:     ()     => api('/api/teams/my'),
    create:         (body) => api('/api/teams', { method: 'POST', body: JSON.stringify(body) }),
    suggest:        (hid)  => api(`/api/teams/suggest/${hid}`),
};

// ─── Request API ──────────────────────────────────────────────────
const Requests = {
    send:     (body) => api('/api/requests', { method: 'POST', body: JSON.stringify(body) }),
    respond:  (body) => api('/api/requests/respond', { method: 'PUT', body: JSON.stringify(body) }),
    incoming: ()     => api('/api/requests/incoming'),
    sent:     ()     => api('/api/requests/sent'),
};

// ─── User API ─────────────────────────────────────────────────────
const Users = {
    getMe:    ()     => api('/api/users/me'),
    getById:  (id)   => api(`/api/users/${id}`),
    update:   (body) => api('/api/users/me', { method: 'PUT', body: JSON.stringify(body) }),
};

// ─── Skills API ───────────────────────────────────────────────────
const Skills = {
    getAll: () => api('/api/skills'),
};

// ─── Chat API ─────────────────────────────────────────────────────
const Chat = {
    getHistory: (teamId) => api(`/api/chat/${teamId}/history`),
};
